package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthMaxFiPointDto {

    private Double monthAvgFiPoint;
    private String maxFiPointName;
    private Integer maxFiPoint;

    public static MonthMaxFiPointDto maxFiPointFrom(Double monthAvgFiPoint, String maxFiPointName, Integer maxFiPoint) {
        return new MonthMaxFiPointDto(monthAvgFiPoint, maxFiPointName, maxFiPoint);
    }
}
