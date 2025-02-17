package app.sync.domain.user.dto.request;

import java.io.Serializable;

public record UserAuthenticateDto(
    String email,
    String password
) implements Serializable {
    // ...
}