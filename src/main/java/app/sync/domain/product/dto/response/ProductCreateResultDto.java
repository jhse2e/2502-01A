package app.sync.domain.product.dto.response;

import java.io.Serializable;

public record ProductCreateResultDto(
    Long id
) implements Serializable {
    // ...
}