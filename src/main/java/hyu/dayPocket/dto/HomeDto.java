package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HomeDto {

    private int month;
    private int day;
    private Long fiScore;
    private Integer fiPoint;
    private DayMaxFiScoreDto maxFiScoreDto;
    private MonthMaxFiPointDto maxFiPointDto;

    public static HomeDto mainFrom(int month, int day, Long fiScore, Integer fiPoint,
                                   DayMaxFiScoreDto maxFiScoreDto, MonthMaxFiPointDto maxFiPointDto) {
        return new HomeDto(month, day, fiScore, fiPoint, maxFiScoreDto, maxFiPointDto);
    }

}
