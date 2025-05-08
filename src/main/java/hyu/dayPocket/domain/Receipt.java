package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private String business;

    private Integer spentMoney;

    private LocalDateTime spentTime;

    @ManyToOne
    @JoinColumn(name = "memberChallenge_id")
    private MemberChallenge memberChallenge;


}
