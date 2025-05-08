package hyu.dayPocket.domain;

import jakarta.persistence.*;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberChallenge_id")
    private MemberChallenge memberChallenge;
}
