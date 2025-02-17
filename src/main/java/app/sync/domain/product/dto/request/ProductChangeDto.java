package app.sync.domain.product.dto.request;

import java.io.Serializable;

public record ProductChangeDto(
    String name,
    String image,
    Integer price,
    Integer stock
) implements Serializable {
    // ...
}