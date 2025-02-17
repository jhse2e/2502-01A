package app.sync.domain.product.dto.response;

import app.sync.domain.product.db.ProductStatusType;

import java.io.Serializable;

public record ProductGetResultDto(
    Long id,
    String name,
    Integer price,
    Integer stock,
    ProductStatusType statusType
) implements Serializable {
    // ...
}