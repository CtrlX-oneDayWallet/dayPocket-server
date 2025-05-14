package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupData {
    private String name;
    private String password;
    private String phoneNumber;
}
