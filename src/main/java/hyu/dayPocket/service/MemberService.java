package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.AssetDto;
import hyu.dayPocket.dto.DayMaxFiScoreDto;
import hyu.dayPocket.dto.MonthMaxFiPointDto;
import hyu.dayPocket.dto.HomeDto;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private  final MemberRepository memberRepository;
    private final DateService dateService;

    public DayMaxFiScoreDto getDayMaxFiScoreDto(){
        List<Member> dayMaxFiScore = memberRepository.findDayMaxFiScore();
        Long dayAvgFiScore = memberRepository.findDayAvgFiScore();
        String maxFiScoreName = dayMaxFiScore.get(0).getName();
        Long maxFiScore = dayMaxFiScore.get(0).getFiScore();
        DayMaxFiScoreDto dayMaxFiScoreDto = DayMaxFiScoreDto.maxFiScoreFrom(dayAvgFiScore, maxFiScoreName, maxFiScore);
        return dayMaxFiScoreDto;
    }

    public MonthMaxFiPointDto getMonthMaxFiPointDto(){
        List<Member> monthMaxFiPoint = memberRepository.findMonthMaxFiPoint();
        Integer monthAvgFiPoint = memberRepository.findMonthAvgFiPoint();
        String maxFiPointName = monthMaxFiPoint.get(0).getName();
        Integer maxFiPoint = monthMaxFiPoint.get(0).getFiPoint();
        MonthMaxFiPointDto monthMaxFiPointDto = MonthMaxFiPointDto.maxFiPointFrom(monthAvgFiPoint, maxFiPointName, maxFiPoint);
        return monthMaxFiPointDto;
    }

    public HomeDto getHomeDto(Member member){
        int month = dateService.getLocalDate().getMonthValue();
        int day = dateService.getLocalDate().getDayOfMonth();
        Long fiScore = member.getFiScore();
        Integer fiPoint = member.getFiPoint();
        DayMaxFiScoreDto dayMaxFiScoreDto = getDayMaxFiScoreDto();
        MonthMaxFiPointDto monthMaxFiPointDto = getMonthMaxFiPointDto();

        HomeDto homeDto = HomeDto.mainFrom(month, day, fiScore, fiPoint, dayMaxFiScoreDto, monthMaxFiPointDto);
        return homeDto;
    }


    public AssetDto getAssetDto(Member member) {
        Long asset = member.getAsset();
        Integer targetReceiptFiPoint = member.getTargetReceiptFiPoint();
        Integer receiptFiPoint = member.getReceiptFiPoint();
        Double processPoint  = ((double) receiptFiPoint / (double) targetReceiptFiPoint  * 100);
        Integer leftPoint = targetReceiptFiPoint - receiptFiPoint;
        Integer fiPoint = member.getFiPoint();
        AssetDto assetDto = AssetDto.assetFrom(asset, targetReceiptFiPoint, receiptFiPoint, processPoint, leftPoint, fiPoint);
        return assetDto;
    }
}
