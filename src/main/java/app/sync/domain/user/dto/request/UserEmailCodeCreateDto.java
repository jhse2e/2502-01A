package app.sync.domain.user.dto.request;

import java.io.Serializable;

public record UserEmailCodeCreateDto(
    String email
) implements Serializable {
    // ...
}