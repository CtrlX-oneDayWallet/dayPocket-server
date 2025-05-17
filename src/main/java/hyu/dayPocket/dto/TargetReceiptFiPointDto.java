package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TargetReceiptFiPointDto {

    private Integer targetReceiptFiPoint;


    public static TargetReceiptFiPointDto targetReceiptFiPointFrom(Integer targetReceiptFiPoint) {
        return new TargetReceiptFiPointDto(targetReceiptFiPoint);
    }
}
