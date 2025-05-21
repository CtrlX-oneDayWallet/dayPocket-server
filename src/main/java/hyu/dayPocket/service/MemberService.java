package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.*;
import hyu.dayPocket.exceptions.AuthenticationException;
import hyu.dayPocket.exceptions.MemberNotFoundException;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.utils.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
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
        Double avgFiScore = memberRepository.findAvgFiScore();
        String maxFiScoreName = dayMaxFiScore.get(0).getName();
        Long maxFiScore = dayMaxFiScore.get(0).getFiScore();
        DayMaxFiScoreDto dayMaxFiScoreDto = DayMaxFiScoreDto.maxFiScoreFrom(avgFiScore, maxFiScoreName, maxFiScore);
        return dayMaxFiScoreDto;
    }

    public MonthMaxFiPointDto getMonthMaxFiPointDto(){
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        List<Object[]> monthMaxFiPointSumOrderByMember = memberRepository.findMonthFiPointSumGroupByMember(startOfMonth, endOfMonth);
        Double monthAvgFiPoint = getMonthAvgFiPoint(monthMaxFiPointSumOrderByMember);
        Object[] topMember = monthMaxFiPointSumOrderByMember.get(0);
        Member member = (Member)topMember[0];
        Integer memberFiPoint = (Integer) topMember[1];
        MonthMaxFiPointDto monthMaxFiPointDto = MonthMaxFiPointDto.maxFiPointFrom(monthAvgFiPoint, member.getName(), memberFiPoint);
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
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        Long asset = memberRepository.accumulateFiPointByMember(member);
        Integer targetReceiptFiPoint = member.getTargetReceiptfiPoint();
        Integer receiptFiPoint = member.getReceiptfiPoint();
        Double processPoint  = ((double) receiptFiPoint / (double) targetReceiptFiPoint  * 100);
        Integer leftPoint = targetReceiptFiPoint - receiptFiPoint;
        Integer fiPoint = memberRepository.sumMonthFiPointByMember(startOfMonth, endOfMonth, member);
        AssetDto assetDto = AssetDto.assetFrom(asset, targetReceiptFiPoint, receiptFiPoint, processPoint, leftPoint, fiPoint);
        return assetDto;
    }

    public InfoDto getInfoDto(Member member){
        return InfoDto.infoFrom(member.getName(), member.getPhoneNumber());
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
                        .fiScore(50L)
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

    public double getMonthAvgFiPoint(List<Object[]> result){

        if (result.isEmpty()) return 0.0;

        int totalSum = 0;
        for(Object[] memberFiPoint : result ){
            Long sum = (Long) memberFiPoint[1];
            totalSum += sum;
        }
        return (double) totalSum/ result.size();
    }






}
