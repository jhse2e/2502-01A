package app.sync.infra.payment.client;

import app.sync.infra.payment.client.payload.request.OuterPaymentCancelDto;
import app.sync.infra.payment.client.payload.response.OuterPaymentCancelResultDto;
import app.sync.infra.payment.client.payload.request.OuterPaymentDto;
import app.sync.infra.payment.client.payload.response.OuterPaymentResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentClient {
    private final Environment env;
    private final RestClient paymentRestClient;

    /**
     * 토스페이 결제 승인
     */
    public OuterPaymentResultDto pay(String orderId, String transactionKey, Integer transactionAmount) {
        log.info("[INFO] orderId: {}, transactionKey: {}, transactionAmount: {}", orderId, transactionKey, transactionAmount);

        ResponseEntity<OuterPaymentResultDto> response = paymentRestClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .headers((headers) -> {
                    headers.setBasicAuth(this.getSecretKey());
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(new OuterPaymentDto(orderId, transactionKey, transactionAmount))
                .retrieve()
                .toEntity(OuterPaymentResultDto.class);

        return response.getBody();
    }

    /**
     * 토스페이 결제 취소
     */
    public OuterPaymentCancelResultDto payCancel(String transactionKey, String transactionReason) {
        log.info("[INFO] transactionKey: {}, transactionReason: {}", transactionKey, transactionReason);

        ResponseEntity<OuterPaymentCancelResultDto> response = paymentRestClient.post()
                .uri("https://api.tosspayments.com/v1/payments/{paymentKey}/cancel", transactionKey)
                .headers((headers) -> {
                    headers.setBasicAuth(this.getSecretKey());
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(new OuterPaymentCancelDto(transactionReason))
                .retrieve()
                .toEntity(OuterPaymentCancelResultDto.class);

        return response.getBody();
    }

    private String getSecretKey() {
        log.info("[INFO] Secret Key: {}", env.getProperty("server.payment.secret-key"));
        log.info("[INFO] Secret Key Encoded: {}", Base64.getEncoder().encodeToString((env.getProperty("server.payment.secret-key") + ":").getBytes(StandardCharsets.UTF_8)));

        return Base64.getEncoder().encodeToString((env.getProperty("server.payment.secret-key") + ":").getBytes(StandardCharsets.UTF_8));
    }
}