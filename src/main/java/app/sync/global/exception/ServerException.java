package app.sync.global.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
    private final ServerExceptionType exceptionType;

    public ServerException(ServerExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
