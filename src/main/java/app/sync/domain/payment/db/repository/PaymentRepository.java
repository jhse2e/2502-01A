package app.sync.domain.payment.db.repository;

import app.sync.domain.payment.db.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByUserIdAndOrderId(Long userId, String orderId);
}