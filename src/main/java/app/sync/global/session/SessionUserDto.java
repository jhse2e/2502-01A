package app.sync.global.session;

import java.io.Serializable;

public record SessionUserDto(
    Long userId,
    String userEmail
) implements Serializable {
    // ...
}