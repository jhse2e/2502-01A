package app.sync.domain.user.dto.request;

import java.io.Serializable;

/**
 * @param email
 * @param emailCode
 */
public record UserEmailCodeAuthenticateDto(
    String email,
    String emailCode
) implements Serializable {
    // ...
}