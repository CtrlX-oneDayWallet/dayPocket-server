package hyu.dayPocket.service;

import hyu.dayPocket.exceptions.AuthCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public boolean validateAuthCode(String phoneNumber, String authCode) {
        if (redisTemplate.hasKey(phoneNumber)) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

            String value = valueOperations.get(phoneNumber);
            if (authCode.equals(value)) {
                redisTemplate.delete(phoneNumber);
                return true;
            }
        }

        return false;
    }
}
