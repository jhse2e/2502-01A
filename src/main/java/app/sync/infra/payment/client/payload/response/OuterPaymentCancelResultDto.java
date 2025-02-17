package app.sync.infra.payment.client.payload.response;

import java.io.Serializable;

public record OuterPaymentCancelResultDto(
    String status
) implements Serializable {

    public Boolean isSuccess() {
        return this.status.equals("CANCELED");
    }
}