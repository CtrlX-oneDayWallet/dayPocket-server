package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.PointWithdrawData;
import hyu.dayPocket.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final BankAccountService bankAccountService;

    @PostMapping("/point/withdraw")
    public String withdraw(@RequestBody PointWithdrawData pointWithdrawData, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();

        bankAccountService.withdraw(member, pointWithdrawData.getPoint(), pointWithdrawData.getAccountNumber());
        return "Point Withdrawal success!";
    }
}
