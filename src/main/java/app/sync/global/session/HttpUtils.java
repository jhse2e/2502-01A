package app.sync.global.session;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    public static String getResponseBody(ClientHttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }
}