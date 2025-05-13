package hyu.dayPocket.controller;

import hyu.dayPocket.dto.LoginData;
import hyu.dayPocket.dto.SignupData;
import hyu.dayPocket.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData, HttpServletResponse response) {
        memberService.doLogin(loginData.getPhoneNumber(), loginData.getPassword(), response);
        return "Login Successful!";
    }

    @PostMapping("/phoneNumber")
    public String certifyPhoneNumber(@RequestParam String phoneNumber) {

    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupData signupData) {
        memberService.signUp(signupData.getName(), signupData.getPhoneNumber(), signupData.getPassword());
        return "Signup Successful!";
    }
}
