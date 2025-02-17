package app.sync.infra.payment.client.payload.request;

import java.io.Serializable;

public record OuterPaymentDto(
    String orderId,
    String paymentKey,
    Integer amount
) implements Serializable {
    // ...
}