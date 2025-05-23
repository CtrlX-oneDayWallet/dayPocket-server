package hyu.dayPocket.service;

import hyu.dayPocket.domain.FiPointHistory;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.domain.Receipt;
import hyu.dayPocket.domain.Trade;
import hyu.dayPocket.dto.PhotoRequestDto;
import hyu.dayPocket.dto.StatusDto;
import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.enums.PointPaymentState;
import hyu.dayPocket.repository.FiPointHistoryRepository;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.repository.ReceiptRepository;
import hyu.dayPocket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FiPointService {

    private final MemberRepository memberRepository;
    private final ReceiptRepository receiptRepository;
    private final TradeRepository tradeRepository;
    private final FiPointHistoryRepository fiPointHistoryRepository;


    public void updateFiPoint(PhotoRequestDto photoRequestDto){
        Member member = memberRepository.findById(photoRequestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));

        FiPointHistory fiPointHistory = fiPointHistoryRepository.findTopByMemberIdAndChallengeOrderByDateDesc(member.getId(), photoRequestDto.getType())
                .orElseThrow(() -> new RuntimeException("fiPont로그를 찾을 수 없습니다"));

        if(photoRequestDto.getState() == PointPaymentState.APPROVED){
            if(fiPointHistory.getChallenge() == ChallengeType.RECEIPT){
                Receipt receipt = Receipt.receiptFrom(photoRequestDto.getLocation(), photoRequestDto.getItemName(),
                        photoRequestDto.getAmount());
                member.setFiPoint(member.getFiPoint()+100);
                receiptRepository.save(receipt);
                member.setFiScore(member.getFiScore()+1);
                fiPointHistory.updateFiPontHistory(100, LocalDateTime.now());
            }else if(fiPointHistory.getChallenge() == ChallengeType.TRADE){
                Trade trade = Trade.tradeFrom(photoRequestDto.getAmount(), photoRequestDto.getItemName());
                member.setFiPoint(member.getFiPoint()+500);
                member.setFiScore(member.getFiScore()+5);
                tradeRepository.save(trade);
                fiPointHistory.updateFiPontHistory(500, LocalDateTime.now());
            }
        }else{
            fiPointHistory.updateStateRejected();
        }
    }


    public StatusDto statusDto(Member member, ChallengeType type){
        FiPointHistory fiPointHistory = fiPointHistoryRepository.findTopByMemberIdAndChallengeOrderByDateDesc(member.getId(), type)
                .orElseThrow(() -> new RuntimeException("fiPont로그를 찾을 수 없습니다"));

        PointPaymentState state = fiPointHistory.getState();

        if(state == PointPaymentState.WAITING){
            return new StatusDto(PointPaymentState.WAITING);
        }else if(state == PointPaymentState.APPROVED){
            return new StatusDto(PointPaymentState.APPROVED);
        }else{
            return new StatusDto(PointPaymentState.REJECTED);
        }
    }
}
