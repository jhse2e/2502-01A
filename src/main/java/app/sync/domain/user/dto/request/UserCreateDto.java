package app.sync.domain.user.dto.request;

import java.io.Serializable;

public record UserCreateDto(
    String email,
    String password
) implements Serializable {
    // ...
}