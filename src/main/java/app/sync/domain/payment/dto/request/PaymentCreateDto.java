package app.sync.domain.payment.dto.request;

import java.io.Serializable;

/**
 * @param orderId 주문 ID
 */
public record PaymentCreateDto(
    String orderId
) implements Serializable {
    // ...
}