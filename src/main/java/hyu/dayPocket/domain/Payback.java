package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paybackId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int amount;
    private LocalDateTime date;
}