package hyu.dayPocket.exceptions;

public class SendingSmsFailException extends RuntimeException{
    public SendingSmsFailException(String message) {
        super(message);
    }
}
