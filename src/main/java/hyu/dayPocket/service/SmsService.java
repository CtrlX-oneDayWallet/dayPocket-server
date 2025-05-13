package hyu.dayPocket.service;

import hyu.dayPocket.exceptions.SmsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final StringRedisTemplate redisTemplate;

    private final RestTemplate restTemplate;

    @Value("${aligo.api-key}")
    private String aligoApiKey;

    @Value("${aligo.user-id}")
    private String aligoUserId;

    @Value("${aligo.sender-phone}")
    private String senderPhone;

    public void sendVerificationCode(String phoneNumber) {
        String verificationCode = generateCode();

        redisTemplate.opsForValue().set(phoneNumber, verificationCode, Duration.ofMinutes(5));


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", aligoApiKey);
        params.add("user_id", aligoUserId);
        params.add("sender", senderPhone);
        params.add("receiver", phoneNumber);
        params.add("msg", verificationCode + "는 인증번호입니다.");
        params.add("testmode_yn", "Y");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(params, headers);

        validateSmsRequest(restTemplate.postForEntity("https://apis.aligo.in/send/", request, String.class));
    }

    private String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = secureRandom.nextInt(10);
            authCode.append(digit);
        }

        return authCode.toString();
    }

    private void validateSmsRequest(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new SmsException("요청이 올바르지 않았습니다");
        }
    }
}
