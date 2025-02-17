package app.sync.domain.payment.service;

import app.sync.AppStatic;
import app.sync.domain.order.db.entity.Order;
import app.sync.domain.order.db.entity.OrderProduct;
import app.sync.domain.order.db.repository.OrderProductRepository;
import app.sync.domain.order.db.repository.OrderRepository;
import app.sync.domain.payment.db.PaymentStatusType;
import app.sync.domain.payment.db.entity.Payment;
import app.sync.domain.payment.db.repository.PaymentRepository;
import app.sync.domain.payment.dto.request.PaymentCancelDto;
import app.sync.domain.payment.dto.request.PaymentCompleteDto;
import app.sync.domain.payment.dto.request.PaymentCreateDto;
import app.sync.domain.payment.dto.response.PaymentCancelResultDto;
import app.sync.domain.payment.dto.response.PaymentCompleteResultDto;
import app.sync.domain.payment.dto.response.PaymentCreateResultDto;
import app.sync.domain.payment.dto.response.PaymentGetResultDto;
import app.sync.domain.user.db.entity.User;
import app.sync.domain.user.db.repository.UserRepository;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import app.sync.infra.payment.client.PaymentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;

    /**
     * 결제 조회
     */
    @Transactional(readOnly = true)
    public PaymentGetResultDto getPayment(Long userId, String orderId) {
        Payment payment = paymentRepository.findByUserIdAndOrderId(userId, orderId).orElseThrow(() -> new ServerException(ServerExceptionType.PAYMENT_NOT_EXISTED));

        return new PaymentGetResultDto(payment.getId(), payment.getStatusType().getValue(), payment.getTransactionReason(), payment.getTransactionAmount());
    }

    /**
     * 결제 생성
     */
    @Transactional
    public PaymentCreateResultDto createPayment(Long userId, PaymentCreateDto paymentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServerException(ServerExceptionType.USER_NOT_EXISTED));
        Order order = orderRepository.findByUserIdAndId(userId, paymentDto.orderId()).orElseThrow(() -> new ServerException(ServerExceptionType.ORDER_NOT_EXISTED));
        Payment payment = Payment.create(user, order);

        paymentRepository.save(payment);

        return new PaymentCreateResultDto(payment.getId(), payment.getOrder().getId(), payment.getTransactionAmount());
    }

    /**
     * 결제 승인
     */
    @Transactional
    public PaymentCompleteResultDto completePayment(Long userId, PaymentCompleteDto paymentDto) {
        Payment payment = paymentRepository.findByUserIdAndOrderId(userId, paymentDto.orderId()).orElseThrow(() -> new ServerException(ServerExceptionType.PAYMENT_NOT_EXISTED));
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(payment.getOrder().getId());
        payment.check(userId, paymentDto.transactionAmount(), orderProducts);

        if (paymentClient.pay(paymentDto.orderId(), paymentDto.transactionKey(), paymentDto.transactionAmount()).isSuccess()) {
            payment.processPayment(orderProducts, paymentDto.transactionKey());
        } else {
            if (paymentClient.payCancel(paymentDto.transactionKey(), AppStatic.PAYMENT_COMPLETE_FAILED_MESSAGE).isSuccess()) {
                payment.processPaymentCancel(PaymentStatusType.FAILED.getValue());
            } else {
                throw new ServerException(ServerExceptionType.PAYMENT_CANCEL_FAILED);
            }
        }

        return new PaymentCompleteResultDto(payment.getId());
    }

    /**
     * 결제 취소
     */
    @Transactional
    public PaymentCancelResultDto cancelPayment(Long userId, PaymentCancelDto paymentDto) {
        Payment payment = paymentRepository.findByUserIdAndOrderId(userId, paymentDto.orderId()).orElseThrow(() -> new ServerException(ServerExceptionType.PAYMENT_NOT_EXISTED));
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(payment.getOrder().getId());

        // 결제 준비 상태인 경우
        if (payment.isPaymentReady()) {
            payment.updateByPaymentCanceled(PaymentStatusType.CANCELED.getValue());

            return new PaymentCancelResultDto(payment.getId());
        }

        // 결제 취소 또는 결제 실패 상태인 경우
        if (payment.isPaymentCanceled() || payment.isPaymentFailed()) {
            throw new ServerException(ServerExceptionType.PAYMENT_NOT_CANCELABLE);
        }

        // 결제 완료 상태인 경우
        if (paymentClient.payCancel(payment.getTransactionKey(), paymentDto.transactionReason()).isSuccess()) {
            payment.processPaymentCancel(orderProducts, paymentDto.transactionReason());
        } else {
            throw new ServerException(ServerExceptionType.PAYMENT_CANCEL_FAILED);
        }

        return new PaymentCancelResultDto(payment.getId());
    }
}