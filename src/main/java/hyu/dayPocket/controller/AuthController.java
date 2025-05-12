package hyu.dayPocket.controller;

import hyu.dayPocket.dto.LoginData;
import hyu.dayPocket.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginData loginData, HttpServletResponse response) {
        memberService.doLogin(loginData.getPhoneNumber(), loginData.getPassword(), response);
        return "Login Successful!";
    }
}
