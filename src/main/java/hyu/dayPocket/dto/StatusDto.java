package hyu.dayPocket.dto;

import hyu.dayPocket.enums.PointPaymentState;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusDto {

    PointPaymentState state;

}
