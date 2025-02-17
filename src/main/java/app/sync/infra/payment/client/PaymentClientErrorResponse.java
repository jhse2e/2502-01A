package app.sync.infra.payment.client;

import java.io.Serializable;

public record PaymentClientErrorResponse(
    String code,
    String message
) implements Serializable {
    // ...
}