package hyu.dayPocket.exceptions;

public class AuthCodeNotFoundException extends RuntimeException{
    public AuthCodeNotFoundException(String phoneNumber) {
        super("인증번호가 존재하지 않거나 만료되었습니다. (phone: " + phoneNumber + ")");
    }
}
