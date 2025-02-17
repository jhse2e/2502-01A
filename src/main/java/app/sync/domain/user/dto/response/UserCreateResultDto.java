package app.sync.domain.user.dto.response;

import java.io.Serializable;

public record UserCreateResultDto(
    Long id
) implements Serializable {
    // ...
}