package hyu.dayPocket.service;

import hyu.dayPocket.domain.BankAccount;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.repository.BankAccountRepository;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
@Slf4j
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
        validateIfMemberHasEnoughPoint(member, usedPoint);
        log.info("성함: " +  member.getName() + "\n"
                + "핸드폰 번호" + member.getPhoneNumber() + "\n"
                + "이 유저가 " + usedPoint + "를 사용함\n");

        member.usePoint(usedPoint);
    }

    private void validateIfMemberHasEnoughPoint(Member member, Integer usedPoint) {
        if (member.getFiPoint() < usedPoint) {
            throw new IllegalArgumentException("해당 유저는 입력한 포인트만큼 가지고 있지 않습니다.");
        }
    }
}
