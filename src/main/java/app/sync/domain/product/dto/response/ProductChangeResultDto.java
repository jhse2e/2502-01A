package app.sync.domain.product.dto.response;

import java.io.Serializable;

public record ProductChangeResultDto(
    Long id
) implements Serializable {
    // ...
}