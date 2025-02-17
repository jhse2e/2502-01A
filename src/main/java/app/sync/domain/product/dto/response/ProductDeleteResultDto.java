package app.sync.domain.product.dto.response;

import java.io.Serializable;

public record ProductDeleteResultDto(
    Long id
) implements Serializable {
    // ...
}