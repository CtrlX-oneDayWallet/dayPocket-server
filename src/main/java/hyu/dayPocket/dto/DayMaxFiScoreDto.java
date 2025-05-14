package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayMaxFiScoreDto {

    private Long dayMaxFiScore;
    private String dayMaxFiScoreName;
    private Long dayAvgFiScore;

    public static DayMaxFiScoreDto maxFiScoreFrom(Long dayMaxFiScore, String dayMaxFiScoreName, Long dayAvgFiScore) {
        return new DayMaxFiScoreDto(dayAvgFiScore, dayMaxFiScoreName, dayMaxFiScore);
    }
}
