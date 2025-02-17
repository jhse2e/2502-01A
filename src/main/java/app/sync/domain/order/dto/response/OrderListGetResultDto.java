package app.sync.domain.order.dto.response;

import java.io.Serializable;
import java.util.List;

public record OrderListGetResultDto(
    List<OrderDto> orders
) implements Serializable {

    public record OrderDto(
        String id,
        String status,
        Integer amount,
        List<OrderProductDto> orderProducts
    ) implements Serializable {
        // ...
    }

    public record OrderProductDto(
        String name,
        Integer price,
        Integer quantity
    ) implements Serializable {
        // ...
    }
}