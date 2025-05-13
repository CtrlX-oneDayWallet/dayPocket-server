package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthCodeVerificationData {
    private String authCode;
    private String phoneNumber;
}
