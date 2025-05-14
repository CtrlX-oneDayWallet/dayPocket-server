package hyu.dayPocket.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateService {

    public LocalDateTime getLocalDate(){
        LocalDateTime currentTIme = LocalDateTime.now();
        return currentTIme;
    }
}
