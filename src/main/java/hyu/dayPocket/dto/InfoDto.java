package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoDto {

    private String name;
    private String phoneNumber;

    public static InfoDto infoFrom(String name, String phoneNumber) {
        return new InfoDto(name, phoneNumber);
    }
}
