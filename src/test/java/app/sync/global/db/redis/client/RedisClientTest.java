package app.sync.global.db.redis.client;

import app.sync.global.db.redis.RedisTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(value = {"local-test"})
@Import(value = RedisTestConfig.class)
public class RedisClientTest {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName(value = "Redis DB에 데이터를 등록한다.")
    void cacheData() {
        // Given
        redisClient.cache("key", "value", 60L, TimeUnit.SECONDS);

        // When
        String result = redisTemplate.opsForValue().get("key");

        // Then
        assertThat(result).isEqualTo("value");
    }

    @Test
    @DisplayName(value = "Redis DB에 Key/Value 데이터가 있을 경우 true 값을 반환한다.")
    void equalsValueIsTrue() {
        // Given
        redisClient.cache("key", "value", 60L, TimeUnit.SECONDS);

        // When, Then
        assertThat(redisClient.equalsValue("key", "value")).isTrue();
    }

    @ParameterizedTest
    @DisplayName(value = "Redis DB에 Key 또는 Value 중 하나라도 없을 경우 false 값을 반환한다.")
    @CsvSource(value = {"key1,value,false", "key,value1,false"})
    void equalsValueIsFalse(String key, String value, Boolean result) {
        // Given
        redisClient.cache("key", "value", 60L, TimeUnit.SECONDS);

        // When, Then
        assertThat(redisClient.equalsValue(key, value)).isEqualTo(result);
    }
}