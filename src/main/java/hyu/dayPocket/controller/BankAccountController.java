package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.domain.BankAccount;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.BankAccountData;
import hyu.dayPocket.service.BankAccountService;
import hyu.dayPocket.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final AuthUtils authUtils;

    @PostMapping("/bank/account")
    public String addBankAccount(@RequestBody BankAccountData bankAccountData) {
        CustomUserDetails userDetails = authUtils.getRequestMember();
        Member member = userDetails.getMember();

        bankAccountService.saveAccount(member, bankAccountData.getBank(), bankAccountData.getPassword(), bankAccountData.getAccountNumber());
        return "save Bank Account Success!";
    }
}
