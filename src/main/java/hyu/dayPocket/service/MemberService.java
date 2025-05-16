package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.AssetDto;
import hyu.dayPocket.dto.DayMaxFiScoreDto;
import hyu.dayPocket.dto.MonthMaxFiPointDto;
import hyu.dayPocket.dto.HomeDto;
import hyu.dayPocket.exceptions.AuthenticationException;
import hyu.dayPocket.exceptions.MemberNotFoundException;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.utils.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final Integer maxAge;

    public MemberService(
            MemberRepository memberRepository,
            JwtTokenUtils jwtTokenUtils,
            @Value("${jwt.refresh-token-expire-time}") Integer maxAge
    ) {
        this.memberRepository = memberRepository;
        this.jwtTokenUtils = jwtTokenUtils;
        this.maxAge = maxAge;
    }

    public DayMaxFiScoreDto getDayMaxFiScoreDto(){
        List<Member> dayMaxFiScore = memberRepository.findDayMaxFiScoreMember();
        Double dayAvgFiScore = memberRepository.findDayAvgFiScore();
        String maxFiScoreName = dayMaxFiScore.get(0).getName();
        Long maxFiScore = dayMaxFiScore.get(0).getFiScore();
        DayMaxFiScoreDto dayMaxFiScoreDto = DayMaxFiScoreDto.maxFiScoreFrom(dayAvgFiScore, maxFiScoreName, maxFiScore);
        return dayMaxFiScoreDto;
    }

    public MonthMaxFiPointDto getMonthMaxFiPointDto(){
        List<Member> monthMaxFiPoint = memberRepository.findMonthMaxFiPointMember();
        Double monthAvgFiPoint = memberRepository.findMonthAvgFiPoint();
        String maxFiPointName = monthMaxFiPoint.get(0).getName();
        Integer maxFiPoint = monthMaxFiPoint.get(0).getFiPoint();
        MonthMaxFiPointDto monthMaxFiPointDto = MonthMaxFiPointDto.maxFiPointFrom(monthAvgFiPoint, maxFiPointName, maxFiPoint);
        return monthMaxFiPointDto;
    }

    public HomeDto getHomeDto(Member member) {
        Long fiScore = member.getFiScore();
        Integer fiPoint = member.getFiPoint();
        DayMaxFiScoreDto dayMaxFiScoreDto = getDayMaxFiScoreDto();
        MonthMaxFiPointDto monthMaxFiPointDto = getMonthMaxFiPointDto();

        HomeDto homeDto = HomeDto.mainFrom(fiScore, fiPoint, dayMaxFiScoreDto, monthMaxFiPointDto);
        return homeDto;
    }


    public AssetDto getAssetDto(Member member) {
        Long asset = member.getAsset();
        Integer targetReceiptFiPoint = member.getTargetReceiptfiPoint();
        Integer receiptFiPoint = member.getReceiptfiPoint();
        Double processPoint  = ((double) receiptFiPoint / (double) targetReceiptFiPoint  * 100);
        Integer leftPoint = targetReceiptFiPoint - receiptFiPoint;
        Integer fiPoint = member.getFiPoint();
        AssetDto assetDto = AssetDto.assetFrom(asset, targetReceiptFiPoint, receiptFiPoint, processPoint, leftPoint, fiPoint);
        return assetDto;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException("Cannot find member with id" + id));
    }

    public Member getMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new MemberNotFoundException("Cannot find member with phone number" + phoneNumber)
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
