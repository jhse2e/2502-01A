package app.sync.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http://localhost:8000/swagger-ui/index.html
 */
@Slf4j
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title("상품 주문/결제 서비스")
                .version("1.0")
                .description("상품 주문/결제 서비스 API");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Session");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Session", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(info);
    }

    @Bean
    public GroupedOpenApi userOpenApis() {
        String[] paths = {"/api/users/**"};
        String[] packagesToScan = {"app.sync.domain.user"};

        return GroupedOpenApi.builder().group("User")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi productOpenApis() {
        String[] paths = {"/api/products/**"};
        String[] packagesToScan = {"app.sync.domain.product"};

        return GroupedOpenApi.builder().group("Product")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi orderOpenApis() {
        String[] paths = {"/api/orders/**"};
        String[] packagesToScan = {"app.sync.domain.order"};

        return GroupedOpenApi.builder().group("Order")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi paymentOpenApis() {
        String[] paths = {"/api/payments/**"};
        String[] packagesToScan = {"app.sync.domain.payment"};

        return GroupedOpenApi.builder().group("Payment")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }
}