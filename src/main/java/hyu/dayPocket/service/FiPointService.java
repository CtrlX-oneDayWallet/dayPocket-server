package hyu.dayPocket.service;

import hyu.dayPocket.domain.FiPointHistory;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.domain.Receipt;
import hyu.dayPocket.domain.Trade;
import hyu.dayPocket.dto.PhotoRequestDto;
import hyu.dayPocket.repository.FiPointHistoryRepository;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.repository.ReceiptRepository;
import hyu.dayPocket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiPointService {

    private final MemberRepository memberRepository;
    private final ReceiptRepository receiptRepository;
    private final TradeRepository tradeRepository;
    private final FiPointHistoryRepository fiPointHistoryRepository;


    public void addFiPoint(PhotoRequestDto photoRequestDto){
        Member member = memberRepository.findById(photoRequestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));

        if(photoRequestDto.getType().equals("RECEIPT")){
            Receipt receipt = Receipt.receiptFrom(photoRequestDto.getLocation(), photoRequestDto.getItemName(),
                    photoRequestDto.getAmount());
            member.setFiPoint(member.getFiPoint()+100);
            receiptRepository.save(receipt);
            FiPointHistory fiPointHistory = FiPointHistory.fiPointHistoryFrom(member, 100, LocalDateTime.now());
            fiPointHistoryRepository.save(fiPointHistory);
            member.setFiScore(member.getFiScore()+1);
        }else if(photoRequestDto.getType().equals("TRADE")){
            Trade trade = Trade.tradeFrom(photoRequestDto.getAmount(), photoRequestDto.getItemName());
            member.setFiPoint(member.getFiPoint()+500);
            tradeRepository.save(trade);
            FiPointHistory fiPointHistory = FiPointHistory.fiPointHistoryFrom(member, 500, LocalDateTime.now());
            fiPointHistoryRepository.save(fiPointHistory);
            member.setFiScore(member.getFiScore()+5);
        }
    }
}
