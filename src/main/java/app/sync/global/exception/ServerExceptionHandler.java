package app.sync.global.exception;

import app.sync.global.api.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ServerExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ServerResponse<Void>> handle(ServerException exception) {
        ServerResponse<Void> response = ServerResponse.create(exception.getExceptionType());

        return new ResponseEntity<>(response, response.status());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ServerResponse<Void>> handle(RuntimeException exception) {
        ServerResponse<Void> response = ServerResponse.create(ServerExceptionType.FAILED);

        return new ResponseEntity<>(response, response.status());
    }
}