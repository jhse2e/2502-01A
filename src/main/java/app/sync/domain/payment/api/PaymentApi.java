package app.sync.domain.payment.api;

import app.sync.domain.payment.dto.request.PaymentCancelDto;
import app.sync.domain.payment.dto.request.PaymentCompleteDto;
import app.sync.domain.payment.dto.request.PaymentCreateDto;
import app.sync.domain.payment.dto.response.PaymentCancelResultDto;
import app.sync.domain.payment.dto.response.PaymentCompleteResultDto;
import app.sync.domain.payment.dto.response.PaymentCreateResultDto;
import app.sync.domain.payment.dto.response.PaymentGetResultDto;
import app.sync.domain.payment.service.PaymentService;
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
public class PaymentApi {
    private final PaymentService paymentService;

    @Operation(summary = "결제 조회", description = "")
    @GetMapping(value = {"/api/orders/{orderId}/payments"})
    public ResponseEntity<ServerResponse<PaymentGetResultDto>> getPayment(
        @PathVariable(required = true) String orderId
    ) {
        ServerResponse<PaymentGetResultDto> response = ServerResponse.ok(paymentService.getPayment(UserUtils.getCurrentUser(), orderId));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "결제 생성", description = "")
    @PostMapping(value = {"/api/payments"})
    public ResponseEntity<ServerResponse<PaymentCreateResultDto>> createPayment(
        @RequestBody(required = true) PaymentCreateDto paymentDto
    ) {
        ServerResponse<PaymentCreateResultDto> response = ServerResponse.ok(paymentService.createPayment(UserUtils.getCurrentUser(), paymentDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "결제 승인", description = "")
    @PostMapping(value = {"/api/payments/complete"})
    public ResponseEntity<ServerResponse<PaymentCompleteResultDto>> completePayment(
        @RequestBody(required = true) PaymentCompleteDto paymentDto
    ) {
        System.out.println("결제 승인");
        ServerResponse<PaymentCompleteResultDto> response = ServerResponse.ok(paymentService.completePayment(UserUtils.getCurrentUser(), paymentDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "결제 취소", description = "")
    @PostMapping(value = {"/api/payments/cancel"})
    public ResponseEntity<ServerResponse<PaymentCancelResultDto>> cancelPayment(
        @RequestBody(required = true) PaymentCancelDto paymentDto
    ) {
        System.out.println("결제 취소");
        ServerResponse<PaymentCancelResultDto> response = ServerResponse.ok(paymentService.cancelPayment(UserUtils.getCurrentUser(), paymentDto));

        return new ResponseEntity<>(response, response.status());
    }
}