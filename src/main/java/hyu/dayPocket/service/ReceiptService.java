package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.TargetReceiptFiPointDto;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public final MemberRepository memberRepository;

    @Transactional
    public void setTargetReceiptFiPoint(Member member, Integer targetReceiptFiPoint) {
        TargetReceiptFiPointDto dto = TargetReceiptFiPointDto.targetReceiptFiPointFrom(targetReceiptFiPoint);
        member.updateTargetReceiptFiPoint(dto.getTargetReceiptFiPoint());
    }



}
