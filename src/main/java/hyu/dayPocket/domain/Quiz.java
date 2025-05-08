package hyu.dayPocket.domain;

import jakarta.persistence.*;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private Boolean answer;

    private Integer correctAnswerCount;

    @ManyToOne
    @JoinColumn(name = "memberChallenge_id")
    private MemberChallenge memberChallenge;


}

