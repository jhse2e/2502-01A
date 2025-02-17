package app.sync.infra.payment.client.payload.response;

import java.io.Serializable;

public record OuterPaymentResultDto(
    String status
) implements Serializable {

    public Boolean isSuccess() {
        return this.status.equals("DONE");
    }
}