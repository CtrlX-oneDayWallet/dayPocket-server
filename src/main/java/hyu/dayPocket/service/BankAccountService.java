package hyu.dayPocket.service;

import hyu.dayPocket.domain.BankAccount;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.domain.WithDrawalHistory;
import hyu.dayPocket.enums.WithdrawalState;
import hyu.dayPocket.repository.BankAccountRepository;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.repository.WithdrawalHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final WithdrawalHistoryRepository withdrawalHistoryRepository;

    @Transactional
    public void saveAccount(Member member, String bank, Integer password, String accountNumber) {
        bankAccountRepository.save(BankAccount.builder()
                .bankName(bank)
                .accountNumber(accountNumber)
                .owner(member)
                .password(password)
                .build());
    }

    @Transactional
    public void withdraw(Member member, Integer usedPoint) {
        validateIfMemberHasEnoughPoint(member, usedPoint);
        log.info("성함: " +  member.getName()
                + " 핸드폰 번호" + member.getPhoneNumber()
                + " 이 유저가 " + usedPoint + "포인트를 사용함");

        member.usePoint(usedPoint);
        withdrawalHistoryRepository.save(
                WithDrawalHistory.builder()
                        .member(member)
                        .amount(usedPoint)
                        .withdrawalState(WithdrawalState.NOT_WITHDRAWAL)
                        .build()
        );
    }

    private void validateIfMemberHasEnoughPoint(Member member, Integer usedPoint) {
        if (member.getFiPoint() < usedPoint) {
            throw new IllegalArgumentException("해당 유저는 입력한 포인트만큼 가지고 있지 않습니다.");
        }
    }
}
