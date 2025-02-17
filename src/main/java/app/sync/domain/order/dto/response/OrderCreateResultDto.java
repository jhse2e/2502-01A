package app.sync.domain.order.dto.response;

import java.io.Serializable;

/**
 * @param id 주문 ID
 */
public record OrderCreateResultDto(
    String id
) implements Serializable {
    // ...
}