package app.sync.domain.order.db.entity;

import app.sync.domain.product.db.entity.Product;
import app.sync.global.db.rds.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 주문 상품
 */
@Getter
@Entity
@Table(
    name = "order_product",
    indexes = {
        // @Index(name = "order_product_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "order_product_uk_1", columnNames = {"order_id", "product_id"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends AbstractEntity implements Serializable {

    /**
     * 주문 상품 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 주문 상품 가격
     */
    @Column(nullable = false)
    private Integer price;

    /**
     * 주문 상품 수량
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * 주문
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    /**
     * 상품
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder(access = AccessLevel.PUBLIC)
    public OrderProduct(Order order, Product product, Integer price, Integer quantity) {
        this.order = order;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * 주문 상품을 생성한다.
     */
    public static OrderProduct create(Order order, Product product, Integer price, Integer quantity) {
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .price(price)
                .quantity(quantity)
                .build();
    }

    /**
     * 상품을 주문 처리한다.
     */
    public OrderProduct updateProductStockByOrder() {
        this.product.updateStockByOrder(this.quantity);

        return this;
    }

    /**
     * 상품을 주문 취소 처리한다.
     */
    public OrderProduct updateProductStockByOrderCancel() {
        this.product.updateStockByOrderCancel(this.quantity);

        return this;
    }

    public Boolean hasProductStock() {
        return this.product.hasStock(this.quantity);
    }
}