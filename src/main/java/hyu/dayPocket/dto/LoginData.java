package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginData {
    private String phoneNumber;
    private String password;
}
