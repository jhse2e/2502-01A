package app.sync.domain.product.dto.response;

import java.io.Serializable;
import java.util.List;

public record ProductListGetResultDto(
    List<ProductDto> products
) implements Serializable {

    public record ProductDto(
        Long id,
        String name,
        Integer price
    ) implements Serializable {
        // ...
    }
}