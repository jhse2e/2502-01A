package app.sync.domain.order.db.entity;

import app.sync.domain.order.db.OrderStatusType;
import app.sync.domain.order.db.support.OrderIdentifierGenerator;
import app.sync.domain.user.db.entity.User;
import app.sync.global.db.rds.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;

/**
 * 주문
 */
@Getter
@Entity
@Table(
    name = "order",
    indexes = {
        // @Index(name = "order_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "order_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AbstractEntity implements Serializable {

    /**
     * 주문 ID
     */
    @Id
    @GeneratedValue(generator = "order_id")
    @GenericGenerator(name = "order_id", type = OrderIdentifierGenerator.class, parameters = @org.hibernate.annotations.Parameter(name = "separator", value = "R"))
    private String id;

    /**
     * 주문 금액
     */
    @Column(nullable = false)
    private Integer amount;

    /**
     * 주문 상태 유형
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatusType statusType;

    /**
     * 회원
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder(access = AccessLevel.PUBLIC)
    public Order(User user, Integer amount, OrderStatusType statusType) {
        this.user = user;
        this.amount = amount;
        this.statusType = statusType;
    }

    /**
     * 주문을 생성한다. (주문 금액 설정 필요)
     */
    public static Order create(User user) {
        return Order.builder()
                .user(user)
                .amount(0)
                .statusType(OrderStatusType.READY)
                .build();
    }

    /**
     * 회원 ID를 비교한다.
     */
    public Boolean equalsUserId(Long userId) {
        return this.user.getId().equals(userId);
    }

    /**
     * 주문 금액을 비교한다.
     */
    public Boolean equalsAmount(Integer amount) {
        return this.amount.equals(amount);
    }

    /**
     * 주문을 완료 처리한다.
     */
    public Order updateByOrderCompleted() {
        this.updateStatus(OrderStatusType.COMPLETED);

        return this;
    }

    /**
     * 주문을 취소 처리한다.
     */
    public Order updateByOrderCanceled() {
        this.updateStatus(OrderStatusType.CANCELED);

        return this;
    }

    /**
     * 주문 금액을 수정한다.
     */
    public Order updateAmountByOrder(Integer amount) {
        this.amount = amount;

        return this;
    }

    /**
     * 주문 상태를 수정한다.
     */
    public Order updateStatus(OrderStatusType statusType) {
        this.statusType = statusType;

        return this;
    }
}