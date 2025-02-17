package app.sync;

import app.sync.domain.product.dto.request.ProductCreateDto;
import app.sync.domain.product.service.ProductService;
import app.sync.domain.user.dto.request.UserCreateDto;
import app.sync.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppDataSetter {
    private final UserService userService;
    private final ProductService productService;

    @Order(value = 1)
    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        this.createUsers();
        this.createProducts();
    }

    private void createUsers() {
        for (int i = 1; i <= 10; i++) {
            userService.createUser(new UserCreateDto(
                    String.format("test%d@test.com", i),
                    "12345"
            ));
        }
    }

    private void createProducts() {
        for (int i = 1; i <= 10; i++) {
            productService.createProduct(new ProductCreateDto(
                    String.format("상품%d", i),
                    "",
                    i * 1000,
                    i * 10
            ));
        }
    }
}