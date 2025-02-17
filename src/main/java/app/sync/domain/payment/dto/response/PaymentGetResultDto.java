package app.sync.domain.payment.dto.response;

import java.io.Serializable;

/**
 * @param id 결제 ID
 * @param status 결제 상태
 * @param amount 결제 금액
 */
public record PaymentGetResultDto(
    String id,
    String status,
    String reason,
    Integer amount
) implements Serializable {
    // ...
}