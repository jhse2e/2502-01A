package app.sync.domain.order.api;

import app.sync.domain.order.dto.request.OrderCreateDto;
import app.sync.domain.order.dto.response.OrderCreateResultDto;
import app.sync.domain.order.dto.response.OrderGetResultDto;
import app.sync.domain.order.dto.response.OrderListGetResultDto;
import app.sync.domain.order.service.OrderService;
import app.sync.domain.user.service.UserUtils;
import app.sync.global.api.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApi {
    private final OrderService orderService;

    @Operation(summary = "주문 조회", description = "")
    @GetMapping(value = {"/api/orders/{orderId}"})
    public ResponseEntity<ServerResponse<OrderGetResultDto>> getOrder(
        @PathVariable(required = true) String orderId
    ) {
        ServerResponse<OrderGetResultDto> response = ServerResponse.ok(orderService.getOrder(UserUtils.getCurrentUser(), orderId));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "주문 목록 조회", description = "")
    @GetMapping(value = {"/api/orders"})
    public ResponseEntity<ServerResponse<OrderListGetResultDto>> getOrders(
        // ...
    ) {
        ServerResponse<OrderListGetResultDto> response = ServerResponse.ok(orderService.getOrders(UserUtils.getCurrentUser()));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "주문 생성", description = "")
    @PostMapping(value = {"/api/orders"})
    public ResponseEntity<ServerResponse<OrderCreateResultDto>> createOrder(
        @RequestBody(required = true) OrderCreateDto orderDto
    ) {
        ServerResponse<OrderCreateResultDto> response = ServerResponse.ok(orderService.createOrder(UserUtils.getCurrentUser(), orderDto));

        return new ResponseEntity<>(response, response.status());
    }
}