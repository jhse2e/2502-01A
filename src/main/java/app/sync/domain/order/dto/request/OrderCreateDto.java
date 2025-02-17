package app.sync.domain.order.dto.request;

import java.io.Serializable;
import java.util.List;

/**
 * @param products
 */
public record OrderCreateDto(
    List<ProductDto> products
) implements Serializable {

    public record ProductDto(
        Long id,
        Integer quantity
    ) implements Serializable {
        // ...
    }
}