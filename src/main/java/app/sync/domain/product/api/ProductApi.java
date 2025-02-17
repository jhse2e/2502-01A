package app.sync.domain.product.api;

import app.sync.domain.product.dto.request.ProductChangeDto;
import app.sync.domain.product.dto.request.ProductCreateDto;
import app.sync.domain.product.dto.response.*;
import app.sync.domain.product.service.ProductService;
import app.sync.global.api.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @Operation(summary = "상품 조회", description = "")
    @GetMapping(value = {"/api/products/{productId}"})
    public ResponseEntity<ServerResponse<ProductGetResultDto>> getProduct(
        @PathVariable(required = true) Long productId
    ) {
        ServerResponse<ProductGetResultDto> response = ServerResponse.ok(productService.getProduct(productId));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "상품 목록 조회", description = "")
    @GetMapping(value = {"/api/products"})
    public ResponseEntity<ServerResponse<ProductListGetResultDto>> getProducts(
        // ...
    ) {
        ServerResponse<ProductListGetResultDto> response = ServerResponse.ok(productService.getProducts());

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "상품 생성", description = "")
    @PostMapping(value = {"/api/products"})
    public ResponseEntity<ServerResponse<ProductCreateResultDto>> createProduct(
        @RequestBody(required = true) ProductCreateDto productDto
    ) {
        ServerResponse<ProductCreateResultDto> response = ServerResponse.ok(productService.createProduct(productDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "상품 수정", description = "")
    @PutMapping(value = {"/api/products/{productId}"})
    public ResponseEntity<ServerResponse<ProductChangeResultDto>> changeProduct(
        @PathVariable(required = true) Long productId,
        @RequestBody(required = true) ProductChangeDto productDto
    ) {
        ServerResponse<ProductChangeResultDto> response = ServerResponse.ok(productService.changeProduct(productId, productDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "상품 삭제", description = "")
    @DeleteMapping(value = {"/api/products/{productId}"})
    public ResponseEntity<ServerResponse<ProductDeleteResultDto>> deleteProduct(
        @PathVariable(required = true) Long productId
    ) {
        ServerResponse<ProductDeleteResultDto> response = ServerResponse.ok(productService.deleteProduct(productId));

        return new ResponseEntity<>(response, response.status());
    }
}