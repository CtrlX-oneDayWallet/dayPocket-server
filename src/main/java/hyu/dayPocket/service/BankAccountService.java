package hyu.dayPocket.service;

import hyu.dayPocket.domain.BankAccount;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.repository.BankAccountRepository;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final MemberRepository memberRepository;

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
    public void withdraw(Member member, Integer usedPoint, String accountNumber) {

    }

    private void validateIfMemberHasEnoughPoint(Member member, Integer usedPoint) {
        if (member.getFiPoint() < usedPoint) {
            throw new
        }
    }
}
