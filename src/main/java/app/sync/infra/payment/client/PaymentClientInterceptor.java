package app.sync.infra.payment.client;

import app.sync.global.session.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class PaymentClientInterceptor implements ClientHttpRequestInterceptor {
    
    @Override
    public @NonNull ClientHttpResponse intercept(
        @NonNull HttpRequest request,
        @NonNull byte[] requestBody,
        @NonNull ClientHttpRequestExecution execution
    ) throws IOException {
        if (!log.isDebugEnabled()) {
            return execution.execute(request, requestBody);
        }

        log.info("[INFO] URI: {}, Method: {}, Headers: {} Body: {}", request.getURI(), request.getMethod(), request.getHeaders(), new String(requestBody, StandardCharsets.UTF_8));
        ClientHttpResponse response = execution.execute(request, requestBody);
        log.info("[INFO] Status: {}, Headers: {}, Body: {}", response.getStatusCode(), response.getHeaders(), HttpUtils.getResponseBody(response));
        return response;
    }
}