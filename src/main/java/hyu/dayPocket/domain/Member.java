package hyu.dayPocket.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(unique = true)
    private String phoneNumber;

    private Long fiScore;

    @Setter
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

    @OneToMany(mappedBy = "owner")
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<WithDrawalHistory> withDrawalHistories = new ArrayList<>();

    public void usePoint(Integer usedPoint) {
        this.fiPoint -= usedPoint;
    }
}