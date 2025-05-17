package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HomeDto {

    private Long fiScore;
    private Integer fiPoint;
    private DayMaxFiScoreDto maxFiScoreDto;
    private MonthMaxFiPointDto maxFiPointDto;

    public static HomeDto mainFrom(Long fiScore, Integer fiPoint,
                                   DayMaxFiScoreDto maxFiScoreDto, MonthMaxFiPointDto maxFiPointDto) {
        return new HomeDto(fiScore, fiPoint, maxFiScoreDto, maxFiPointDto);
    }

}
