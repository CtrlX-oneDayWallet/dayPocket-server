package hyu.dayPocket.controller;

import hyu.dayPocket.dto.AuthCodeVerificationData;
import hyu.dayPocket.dto.LoginData;
import hyu.dayPocket.dto.SignupData;
import hyu.dayPocket.exceptions.AuthCodeNotFoundException;
import hyu.dayPocket.service.MemberService;
import hyu.dayPocket.service.RedisService;
import hyu.dayPocket.service.SmsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final SmsService smsService;
    private final RedisService redisService;

    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData, HttpServletResponse response) {
        memberService.doLogin(loginData.getPhoneNumber(), loginData.getPassword(), response);
        return "Login Successful!";
    }

    @PostMapping("/phoneNumber")
    public String certifyPhoneNumber(@RequestParam String phoneNumber) {
        smsService.sendVerificationCode(phoneNumber);
        return "Phone number is verified!";
    }

    @GetMapping("/auth-code")
    public String verifyAuthCode(@ModelAttribute AuthCodeVerificationData authCodeVerificationData) {
        if (redisService.validateAuthCode(authCodeVerificationData.getPhoneNumber(), authCodeVerificationData.getAuthCode())) {
            return "Auth Code is verified!";
        } else {
            throw new AuthCodeNotFoundException(authCodeVerificationData.getPhoneNumber());
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupData signupData) {
        memberService.signUp(signupData.getName(), signupData.getPhoneNumber(), signupData.getPassword());
        return "Signup Successful!";
    }
}
