package app.sync.global.db.redis.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisClient {
    private final RedisTemplate<String, String> redisTemplate;

    public void cache(String key, String value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public Boolean hasValue(String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }

    public Boolean equalsValue(String key, String value) {
        String target = redisTemplate.opsForValue().get(key);

        return target != null && target.equals(value);
    }
}