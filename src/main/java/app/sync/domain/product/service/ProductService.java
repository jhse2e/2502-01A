package app.sync.domain.product.service;

import app.sync.domain.product.db.entity.Product;
import app.sync.domain.product.dto.request.ProductChangeDto;
import app.sync.domain.product.dto.request.ProductCreateDto;
import app.sync.domain.product.db.repository.ProductRepository;
import app.sync.domain.product.dto.response.*;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 상품 조회
     */
    @Transactional(readOnly = true)
    public ProductGetResultDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ServerException(ServerExceptionType.PRODUCT_NOT_EXISTED));

        return new ProductGetResultDto(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getStatusType());
    }

    /**
     * 상품 목록 조회
     */
    @Transactional(readOnly = true)
    public ProductListGetResultDto getProducts() {
        List<ProductListGetResultDto.ProductDto> productDtos = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            productDtos.add(new ProductListGetResultDto.ProductDto(product.getId(), product.getName(), product.getPrice()));
        }

        return new ProductListGetResultDto(productDtos);
    }

    /**
     * 상품 생성
     */
    @Transactional
    public ProductCreateResultDto createProduct(ProductCreateDto productDto) {
        Product product = productRepository.save(Product.create(productDto.name(), productDto.image(), productDto.price(), productDto.stock()));

        return new ProductCreateResultDto(product.getId());
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductChangeResultDto changeProduct(Long productId, ProductChangeDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ServerException(ServerExceptionType.PRODUCT_NOT_EXISTED));
        product.update(productDto.name(), productDto.image(), productDto.price(), productDto.stock());

        return new ProductChangeResultDto(productId);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public ProductDeleteResultDto deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ServerException(ServerExceptionType.PRODUCT_NOT_EXISTED));
        product.delete();

        return new ProductDeleteResultDto(productId);
    }
}