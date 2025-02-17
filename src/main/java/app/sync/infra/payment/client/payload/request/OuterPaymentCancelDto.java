package app.sync.infra.payment.client.payload.request;

import java.io.Serializable;

/**
 * 부분 결제 취소 시, cancelAmount 값을 추가해야 한다.
 * @param cancelReason 결제 취소 이유
 */
public record OuterPaymentCancelDto(
    String cancelReason
) implements Serializable {
    // ...
}