package app.sync.domain.order.db.repository;

import app.sync.domain.order.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByUserIdAndId(Long userId, String orderId);
    List<Order> findAllByUserId(Long userId);
}