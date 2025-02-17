package app.sync.domain.payment.dto.response;

import java.io.Serializable;

/**
 * @param id 결제 ID
 * @param orderId 주문 ID
 * @param orderAmount 주문 금액
 */
public record PaymentCreateResultDto(
    String id,
    String orderId,
    Integer orderAmount
) implements Serializable {
    // ...
}