package app.sync.domain.product.db.entity;

import app.sync.domain.product.db.ProductStatusType;
import app.sync.global.db.rds.entity.AbstractEntity;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 상품
 */
@Getter
@Entity
@Table(
    name = "product",
    indexes = {
        // @Index(name = "product_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "product_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AbstractEntity implements Serializable {

    /**
     * 상품 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 상품 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 상품 이미지
     */
    @Column(nullable = false)
    private String image;

    /**
     * 상품 가격
     */
    @Column(nullable = false)
    private Integer price;

    /**
     * 상품 재고
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * 상품 상태 유형
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatusType statusType;

    @Builder(access = AccessLevel.PUBLIC)
    public Product(String name, String image, Integer price, Integer stock, ProductStatusType statusType) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.statusType = statusType;
    }

    /**
     * 상품을 생성한다.
     */
    public static Product create(String name, String image, Integer price, Integer stock) {
        return Product.builder()
                .name(name)
                .image(image)
                .price(price)
                .stock(stock)
                .statusType(ProductStatusType.READY)
                .build();
    }

    /**
     * 상품 가격 합계를 가져온다.
     */
    public Integer getPriceSum(Integer quantity) {
        return this.price * quantity;
    }

    /**
     * 상품을 수정한다.
     */
    public Product update(String name, String image, Integer price, Integer stock) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;

        return this;
    }

    public Boolean hasStock(Integer quantity) {
        return this.stock - quantity >= 0;
    }

    /**
     * 상품 재고를 수정한다.
     */
    public Product updateStockByOrder(Integer quantity) {
        if (this.stock - quantity < 0) {
            throw new ServerException(ServerExceptionType.PRODUCT_STOCK_NOT_ENOUGH);
        }

        this.stock = this.stock - quantity;

        return this;
    }

    /**
     * 상품 재고를 수정한다.
     */
    public Product updateStockByOrderCancel(Integer quantity) {
        this.stock = this.stock + quantity;

        return this;
    }

    /**
     * 상품 상태를 수정한다.
     */
    public void updateStatus(ProductStatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * 상품을 삭제 처리한다.
     */
    public Product delete() {
        this.updateStatus(ProductStatusType.DELETED);

        return this;
    }
}