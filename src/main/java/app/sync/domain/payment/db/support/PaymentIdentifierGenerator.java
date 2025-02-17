package app.sync.domain.payment.db.support;

import app.sync.AppStatic;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class PaymentIdentifierGenerator implements IdentifierGenerator, Configurable {
    private String separator;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        separator = ConfigurationHelper.getString("separator", params);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        LocalDateTime current = LocalDateTime.now();

        // 20250101 101010 P 100
        return String.format("%s%s%s%d",
                current.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale.KOREA)),
                current.format(DateTimeFormatter.ofPattern("HHmmss", Locale.KOREA)),
                separator,
                ThreadLocalRandom.current().nextInt(AppStatic.PAYMENT_ID_RANGE_START, AppStatic.PAYMENT_ID_RANGE_STOP));
    }
}