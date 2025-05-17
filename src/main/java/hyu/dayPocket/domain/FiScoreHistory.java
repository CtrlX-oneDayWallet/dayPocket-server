package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FiScoreHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer fiScore;

    private LocalDateTime date;
}
