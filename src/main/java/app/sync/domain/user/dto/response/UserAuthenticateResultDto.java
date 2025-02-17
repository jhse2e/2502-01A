package app.sync.domain.user.dto.response;

import java.io.Serializable;

public record UserAuthenticateResultDto(
    Long id,
    String email
) implements Serializable {
    // ...
}