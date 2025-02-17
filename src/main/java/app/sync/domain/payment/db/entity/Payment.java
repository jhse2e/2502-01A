package app.sync.domain.payment.db.entity;

import app.sync.domain.order.db.entity.Order;
import app.sync.domain.order.db.entity.OrderProduct;
import app.sync.domain.payment.db.PaymentStatusType;
import app.sync.domain.payment.db.support.PaymentIdentifierGenerator;
import app.sync.domain.user.db.entity.User;
import app.sync.global.db.rds.entity.AbstractEntity;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.util.List;

/**
 * 결제
 */
@Getter
@Entity
@Table(
    name = "payment",
    indexes = {
        // @Index(name = "payment_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "payment_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends AbstractEntity implements Serializable {

    /**
     * 결제 ID
     */
    @Id
    @GeneratedValue(generator = "payment_id")
    @GenericGenerator(name = "payment_id", type = PaymentIdentifierGenerator.class, parameters = @org.hibernate.annotations.Parameter(name = "separator", value = "P"))
    private String id;

    /**
     * 결제 거래 키
     */
    @Column(nullable = false)
    private String transactionKey;

    /**
     * 결제 거래 사유
     */
    @Column(nullable = false)
    private String transactionReason;

    /**
     * 결제 거래 금액
     */
    @Column(nullable = false)
    private Integer transactionAmount;

    /**
     * 결제 상태 유형
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatusType statusType;

    /**
     * 회원
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 주문
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Builder(access = AccessLevel.PUBLIC)
    public Payment(User user, Order order, String transactionKey, String transactionReason, Integer transactionAmount, PaymentStatusType statusType) {
        this.user = user;
        this.order = order;
        this.transactionKey = transactionKey;
        this.transactionReason = transactionReason;
        this.transactionAmount = transactionAmount;
        this.statusType = statusType;
    }

    /**
     * 결제를 생성한다.
     */
    public static Payment create(User user, Order order) {
        return Payment.builder()
                .user(user)
                .order(order)
                .transactionKey("")
                .transactionReason("상품 결제")
                .transactionAmount(order.getAmount())
                .statusType(PaymentStatusType.READY)
                .build();
    }

    /**
     * 결제 상태를 확인한다.
     */
    public Boolean isPaymentReady() {
        return this.statusType.isReady();
    }

    /**
     * 결제 상태를 확인한다.
     */
    public Boolean isPaymentCanceled() {
        return this.statusType.isCanceled();
    }

    /**
     * 결제 상태를 확인한다.
     */
    public Boolean isPaymentFailed() {
        return this.statusType.isFailed();
    }

    /**
     * 결제 상태를 확인한다.
     */
    public Boolean isPaymentCompleted() {
        return this.statusType.isCompleted();
    }

    /**
     * 결제 거래 값을 확인한다.
     */
    public Payment check(Long userId, Integer transactionAmount, List<OrderProduct> orderProducts) {
        if (!this.equalsUserId(userId)) {
            throw new ServerException(ServerExceptionType.PAYMENT_TRANSACTION_USER_NOT_MATCHED);
        }

        if (!this.equalsTransactionAmount(transactionAmount)) {
            throw new ServerException(ServerExceptionType.PAYMENT_TRANSACTION_AMOUNT_NOT_MATCHED);
        }

        for (OrderProduct orderProduct : orderProducts) {
            if (!orderProduct.hasProductStock()) {
                throw new ServerException(ServerExceptionType.PRODUCT_STOCK_NOT_ENOUGH);
            }
        }

        return this;
    }

    /**
     * 회원 ID를 비교한다.
     */
    public Boolean equalsUserId(Long userId) {
        return this.order.getUser().getId().equals(userId);
    }

    /**
     * 결제 거래 키를 비교한다.
     */
    public Boolean equalsTransactionKey(String transactionKey) {
        return this.transactionKey.equals(transactionKey);
    }

    /**
     * 결제 거래 금액을 비교한다.
     */
    public Boolean equalsTransactionAmount(Integer transactionAmount) {
        return this.transactionAmount.equals(transactionAmount);
    }

    /**
     * 결제를 처리한다.
     */
    public Payment processPayment(List<OrderProduct> orderProducts, String transactionKey) {
        this.updateByPaymentCompleted(transactionKey);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.updateProductStockByOrder();
        }

        return this;
    }

    /**
     * 결제를 취소 처리한다.
     */
    public Payment processPaymentCancel(String transactionReason) {
        this.updateByPaymentCanceled(transactionReason);

        return this;
    }

    /**
     * 결제를 취소 처리한다.
     */
    public Payment processPaymentCancel(List<OrderProduct> orderProducts, String transactionReason) {
        this.updateByPaymentCanceled(transactionReason);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.updateProductStockByOrderCancel();
        }

        return this;
    }

    /**
     * 결제 및 주문을 완료 처리한다.
     */
    public Payment updateByPaymentCompleted(String transactionKey) {
        this.order.updateByOrderCompleted();
        this.updateStatus(PaymentStatusType.COMPLETED);
        this.updateTransactionKey(transactionKey);

        return this;
    }

    /**
     * 결제 및 주문을 취소 처리한다.
     */
    public Payment updateByPaymentCanceled(String transactionReason) {
        this.order.updateByOrderCanceled();
        this.updateStatus(PaymentStatusType.CANCELED);
        this.updateTransactionReason(transactionReason);

        return this;
    }

    /**
     * 결제 거래 키를 수정한다.
     */
    public Payment updateTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;

        return this;
    }

    /**
     * 결제 거래 이유를 수정한다.
     */
    public Payment updateTransactionReason(String transactionReason) {
        this.transactionReason = transactionReason;

        return this;
    }

    /**
     * 결제 상태를 수정한다.
     */
    public void updateStatus(PaymentStatusType statusType) {
        this.statusType = statusType;
    }
}