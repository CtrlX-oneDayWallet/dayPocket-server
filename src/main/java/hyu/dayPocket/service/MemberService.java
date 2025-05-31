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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final Integer maxAge;
    private final PasswordEncoder passwordEncoder;

    public MemberService(
            MemberRepository memberRepository,
            JwtTokenUtils jwtTokenUtils,
            @Value("${jwt.refresh-token-expire-time}") Integer maxAge,
            PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtTokenUtils = jwtTokenUtils;
        this.maxAge = maxAge;
        this.passwordEncoder = passwordEncoder;
    }

    public DayMaxFiScoreDto getDayMaxFiScoreDto(){
        List<Member> dayMaxFiScore = memberRepository.findDayMaxFiScoreMember();
        Double avg = memberRepository.findAvgFiScore();
        Double avgFiScore = Optional.ofNullable(avg).orElse(0.0);
        String maxFiScoreName = dayMaxFiScore.get(0).getName();
        Long maxFiScore = dayMaxFiScore.get(0).getFiScore();
        DayMaxFiScoreDto dayMaxFiScoreDto = DayMaxFiScoreDto.maxFiScoreFrom(avgFiScore, getMemberNamePrivate(maxFiScoreName), maxFiScore);
        return dayMaxFiScoreDto;
    }

    public MonthMaxFiPointDto getMonthMaxFiPointDto(){
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();;
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        List<Object[]> monthMaxFiPointSumOrderByMember = memberRepository.findMonthFiPointSumGroupByMember(startOfMonth, endOfMonth);
        Double monthAvgFiPoint = getMonthAvgFiPoint(monthMaxFiPointSumOrderByMember);
        Object[] topMember = monthMaxFiPointSumOrderByMember.get(0);
        Member member = (Member)topMember[0];
        Long memberFiPointLong = (Long) topMember[1];
        Integer memberFiPoint = (memberFiPointLong != null) ? memberFiPointLong.intValue() : 0;
        MonthMaxFiPointDto monthMaxFiPointDto = MonthMaxFiPointDto.maxFiPointFrom(monthAvgFiPoint, getMemberNamePrivate(member.getName()), memberFiPoint);
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
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();;
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        Long asset = memberRepository.accumulateFiPointByMember(member);
        Integer targetReceiptFiPoint = member.getTargetReceiptfiPoint();
        Integer receiptFiPoint = member.getReceiptfiPoint();
        double processPoint = ((double) receiptFiPoint / (double) targetReceiptFiPoint) * 100;
        int roundedProcessPoint = (int) Math.round(processPoint);
        Integer leftPoint = targetReceiptFiPoint - receiptFiPoint;
        Long fiPointLong = memberRepository.sumMonthFiPointByMember(startOfMonth, endOfMonth, member);
        Integer fiPoint = fiPointLong != null ? fiPointLong.intValue() : 0;
        AssetDto assetDto = AssetDto.assetFrom(asset, targetReceiptFiPoint, receiptFiPoint, roundedProcessPoint, leftPoint, fiPoint);
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
        if (passwordEncoder.matches(password, member.getPassword())) {
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
                        .password(passwordEncoder.encode(password))
                        .fiScore(50L)
                        .fiPoint(0)
                        .asset(0L)
                        .targetReceiptfiPoint(0)
                        .receiptfiPoint(0)
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

    public String getMemberNamePrivate(String name){
        int length = name.length();
        int middle = length / 2;

        if (length % 2 == 0) {
            return name.substring(0, middle - 1) + "*" + name.substring(middle);
        } else {
            return name.substring(0, middle) + "*" + name.substring(middle + 1);
        }
    }








}
