package hyu.dayPocket.dto;

import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.enums.PointPaymentState;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PhotoRequestDto {

    private ChallengeType type;
    private Long memberId;
    private String itemName;
    private String location;
    private Integer amount;
    private PointPaymentState state;


}
