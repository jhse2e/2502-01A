package app.sync.infra.payment;

import app.sync.infra.payment.client.PaymentClientErrorHandler;
import app.sync.infra.payment.client.PaymentClientInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public RestClient paymentRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));
        requestFactory.setReadTimeout(Duration.ofSeconds(30));

        return RestClient.builder()
                .requestFactory(new BufferingClientHttpRequestFactory(requestFactory))
                .requestInterceptor(new PaymentClientInterceptor())
                .defaultStatusHandler(new PaymentClientErrorHandler(objectMapper))
                .build();
    }
}