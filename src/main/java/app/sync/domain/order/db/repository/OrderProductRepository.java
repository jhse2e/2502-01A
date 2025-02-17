package app.sync.domain.order.db.repository;

import app.sync.domain.order.db.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrderId(String orderId);
}