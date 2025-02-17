package app.sync.global.db.rds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"app.sync.domain.*.db"})
public class JpaConfig {
    // ...
}