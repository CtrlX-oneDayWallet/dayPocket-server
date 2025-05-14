package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthMaxFiPointDto {

    private Integer monthAvgFiPoint;
    private String maxFiPointName;
    private Integer maxFiPoint;

    public static MonthMaxFiPointDto maxFiPointFrom(Integer monthAvgFiPoint, String maxFiPointName, Integer maxFiPoint) {
        return new MonthMaxFiPointDto(monthAvgFiPoint, maxFiPointName, maxFiPoint);
    }
}
