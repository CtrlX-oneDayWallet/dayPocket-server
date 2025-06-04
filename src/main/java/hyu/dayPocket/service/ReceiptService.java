package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.TargetReceiptFiPointDto;
import hyu.dayPocket.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public final MemberRepository memberRepository;

    @Transactional
    public void setTargetReceiptFiPoint(Member member, Integer targetReceiptFiPoint) {
        Member loginMember = memberRepository.findById(member.getId()).orElseThrow(() -> new EntityNotFoundException("Member is not found"));
        loginMember.updateTargetReceiptFiPoint(targetReceiptFiPoint);
    }



}
