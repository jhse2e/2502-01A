package app.sync.domain.order.dto.response;

import java.io.Serializable;

public record OrderGetResultDto(
    String id,
    String status,
    Integer amount
) implements Serializable {
    // ...
}