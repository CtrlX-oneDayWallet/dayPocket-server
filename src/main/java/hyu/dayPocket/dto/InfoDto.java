package hyu.dayPocket.dto;

import hyu.dayPocket.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InfoDto {

    private String name;
    private String phoneNumber;
    private List<String> bankAccountList;

    public static InfoDto infoFrom(String name, String phoneNumber, List<String> bankAccountList) {
        return new InfoDto(name, phoneNumber, bankAccountList);
    }
}
