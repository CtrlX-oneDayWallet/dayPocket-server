package hyu.dayPocket.service;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.TargetReceiptFiPointDto;
import hyu.dayPocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    public final MemberRepository memberRepository;

    public void setTargetReceiptFiPoint(Member member, Integer targetReceiptFiPoint) {
        TargetReceiptFiPointDto dto = TargetReceiptFiPointDto.targetReceiptFiPointFrom(targetReceiptFiPoint);
        member.updateTargetReceiptFiPoint(dto.getTargetReceiptFiPoint());
    }



}
