package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MemberChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @OneToMany(mappedBy = "memberChallenge")
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "memberChallenge")
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "memberChallenge")
    private List<Receipt> receipts = new ArrayList<>();
}

