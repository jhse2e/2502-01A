package app.sync.domain.payment.dto.request;

import java.io.Serializable;

/**
 * @param orderId 주문 ID
 * @param transactionKey 결제 거래 키
 * @param transactionAmount 결제 거래 금액
 */
public record PaymentCompleteDto(
    String orderId,
    String transactionKey,
    Integer transactionAmount
) implements Serializable {
    // ...
}