package app.sync.domain.user.dto.response;

import java.io.Serializable;

public record UserEmailCodeCreateResultDto(
    String email
) implements Serializable {
    // ...
}