package app.sync.domain.product.db.repository;

import app.sync.domain.product.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // ...
}