package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String challengeType;
    private String title;
    private String description;
    private Integer point;

    @OneToMany(mappedBy = "challenge")
    private List<MemberChallenge> memberChallenges = new ArrayList<>();
}
