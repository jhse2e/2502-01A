package app.sync.domain.payment.dto.response;

import java.io.Serializable;

/**
 * @param id 결제 ID
 */
public record PaymentCancelResultDto(
    String id
) implements Serializable {
    // ...
}