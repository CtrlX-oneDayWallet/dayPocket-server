package hyu.dayPocket.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final StringRedisTemplate redisTemplate;

    private DefaultMessageService messageService;

    @Value("${coolsms.sender}")
    private String senderNumber;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;


    @PostConstruct
    public void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey,
                apiSecret,
                "https://api.coolsms.co.kr");
    }

    public boolean sendVerificationCode(String phoneNumber) {
        String verificationCode = generateCode();

        redisTemplate.opsForValue().set(phoneNumber, verificationCode, Duration.ofMinutes(5));

        return sendSms(phoneNumber, "하루 지갑의 인증 번호는 [" + verificationCode + "] 입니다.");
    }

    public boolean sendSms(String to, String text) {
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText(text);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        if (response.getStatusCode().equals("2000")) {
            return true;
        }

        return false;
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

//    private void validateSmsRequest(ResponseEntity<String> response) {
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new SendingSmsFailException("요청이 올바르지 않았습니다");
//        }
//    }
}
