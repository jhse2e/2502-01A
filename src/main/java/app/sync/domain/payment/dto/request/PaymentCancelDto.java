package app.sync.domain.payment.dto.request;

import java.io.Serializable;

/**
 * @param orderId 주문 ID
 * @param transactionReason 결제 거래 이유
 */
public record PaymentCancelDto(
    String orderId,
    String transactionReason
) implements Serializable {
    // ...
}