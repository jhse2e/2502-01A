package app.sync.global.api;

import app.sync.global.exception.ServerExceptionType;
import org.springframework.http.HttpStatus;
import java.io.Serializable;

public record ServerResponse<T>(
    String code,
    String message,
    HttpStatus status,
    T result
) implements Serializable {

    public static <T> ServerResponse<T> ok() {
        return new ServerResponse<>("SUCCESS", "요청을 수행하였습니다.", HttpStatus.OK, null);
    }

    public static <T> ServerResponse<T> ok(T result) {
        return new ServerResponse<>("SUCCESS", "요청을 수행하였습니다.", HttpStatus.OK, result);
    }

    public static <T> ServerResponse<T> create(ServerExceptionType exceptionType) {
        return new ServerResponse<>(exceptionType.getCode(), exceptionType.getMessage(), exceptionType.getStatus(), null);
    }

    public static <T> ServerResponse<T> create(ServerExceptionType exceptionType, HttpStatus status) {
        return new ServerResponse<>(exceptionType.getCode(), exceptionType.getMessage(), status, null);
    }
}