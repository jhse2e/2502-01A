package app.sync.infra.payment.client;

import app.sync.global.session.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class PaymentClientErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            PaymentClientErrorResponse error = objectMapper.readValue(HttpUtils.getResponseBody(response), PaymentClientErrorResponse.class);
            log.info("[INFO] Code: {}, Message: {}", error.code(), error.message());
            throw new HttpClientErrorException(error.message(), response.getStatusCode(), error.code(), null, null, null);
        }

        if (response.getStatusCode().is5xxServerError()) {
            PaymentClientErrorResponse error = objectMapper.readValue(HttpUtils.getResponseBody(response), PaymentClientErrorResponse.class);
            log.info("[INFO] Code: {}, Message: {}", error.code(), error.message());
            throw new HttpServerErrorException(error.message(), response.getStatusCode(), error.code(), null, null, null);
        }
    }
}