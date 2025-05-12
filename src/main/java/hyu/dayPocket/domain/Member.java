package hyu.dayPocket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(unique = true)
    private String phoneNumber;

    private Long fiScore;

    private Integer fiPoint;

    private Long asset;

    private Integer targetReceiptfiPoint;

    private Integer receiptfiPoint;

    @Setter
    private String refreshToken;

    @OneToMany(mappedBy = "member")
    private List<MemberChallenge> memberChallenges = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Payback> payBacks = new ArrayList<>();
}