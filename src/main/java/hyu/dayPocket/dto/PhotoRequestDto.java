package hyu.dayPocket.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PhotoRequestDto {

    private String type;
    private Long memberId;
    private String itemName;
    private String location;
    private Integer amount;


}
