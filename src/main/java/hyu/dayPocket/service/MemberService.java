package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.exceptions.MemberException;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new MemberException("Cannot find member with id" + id));
    }

    public Member getMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new MemberException("Cannot find member with phone number" + phoneNumber)
        );
    }
}
