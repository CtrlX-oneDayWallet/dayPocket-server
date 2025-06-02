package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayMaxFiScoreDto {

    private Long dayMaxFiScore;
    private String dayMaxFiScoreName;
    private Double dayAvgFiScore;

    public static DayMaxFiScoreDto maxFiScoreFrom(Long dayMaxFiScore, String dayMaxFiScoreName, Double dayAvgFiScore) {
        return new DayMaxFiScoreDto(dayMaxFiScore, dayMaxFiScoreName, dayAvgFiScore);
    }
}
