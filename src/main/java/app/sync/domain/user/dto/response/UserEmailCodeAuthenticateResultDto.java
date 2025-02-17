package app.sync.domain.user.dto.response;

import java.io.Serializable;

public record UserEmailCodeAuthenticateResultDto(
    String email
) implements Serializable {
    // ...
}