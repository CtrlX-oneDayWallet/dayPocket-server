package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer spentMoney;

    private String tradeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberChallenge_id")
    private MemberChallenge memberChallenge;

    public static Trade tradeFrom(Integer spentMoney, String tradeItem){
        Trade trade = new Trade();
        trade.spentMoney = spentMoney;
        trade.tradeItem = tradeItem;
        return trade;
    }
}
