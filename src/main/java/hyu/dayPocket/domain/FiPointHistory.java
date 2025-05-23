package hyu.dayPocket.domain;

import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.enums.PointPaymentState;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class FiPointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer fiPoint;

    @Enumerated(EnumType.STRING)
    private ChallengeType challenge;

    @Enumerated(EnumType.STRING)
    private PointPaymentState state;

    private LocalDateTime date;

    public static FiPointHistory fiPointHistoryFrom(Member member, Integer fiPoint, PointPaymentState state, ChallengeType challenge, LocalDateTime date) {
        FiPointHistory fiPointHistory = new FiPointHistory();
        fiPointHistory.member = member;
        fiPointHistory.fiPoint = fiPoint;
        fiPointHistory.state = state;
        fiPointHistory.challenge = challenge;
        fiPointHistory.date = date;
        return fiPointHistory;
    }

    public void updateStateRejected(){
        state = PointPaymentState.REJECTED;
    }

    public void updateFiPontHistory(Integer point , LocalDateTime date){
        state = PointPaymentState.APPROVED;
        this.fiPoint = point;
        this.date = date;
    }

}
