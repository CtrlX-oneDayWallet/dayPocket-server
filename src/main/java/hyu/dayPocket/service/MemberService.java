package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.exceptions.AuthenticationException;
import hyu.dayPocket.exceptions.MemberException;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.utils.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final Integer maxAge;

    public MemberService (
            MemberRepository memberRepository,
            JwtTokenUtils jwtTokenUtils,
            @Value("${jwt.refresh-token-expire-time}") Integer maxAge
    ) {
        this.memberRepository = memberRepository;
        this.jwtTokenUtils = jwtTokenUtils;
        this.maxAge = maxAge;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new MemberException("Cannot find member with id" + id));
    }

    public Member getMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new MemberException("Cannot find member with phone number" + phoneNumber)
        );
    }

    @Transactional
    public void doLogin(String phoneNumber, String password, HttpServletResponse response) {
        Member member = getMemberByPhoneNumber(phoneNumber);
        if (member.getPassword().equals(password)) {
            String token = jwtTokenUtils.generateAccessToken(member);
            String refreshToken = jwtTokenUtils.generateRefreshToken(member);

            response.setHeader("Authorization", "Bearer " + token);
            setRefreshToken(refreshToken,response);
            member.setRefreshToken(refreshToken);
        } else {
            throw new AuthenticationException("Wrong password");
        }
    }

    @Transactional
    public Member signUp(String name,String phoneNumber, String password) {
        return memberRepository.save(
                Member.builder()
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .password(password)
                        .build()
        );
    }

    private void setRefreshToken(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }
}
