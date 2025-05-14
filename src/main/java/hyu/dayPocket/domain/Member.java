package hyu.dayPocket.domain;

import jakarta.persistence.*;
import lombok.Getter;

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
    private String phoneNumber;
    private Long fiScore;
    private Integer fiPoint;
    private Long asset;
    private Integer targetReceiptFiPoint;
    private Integer receiptFiPoint;

    @OneToMany(mappedBy = "member")
    private List<MemberChallenge> memberChallenges = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Payback> payBacks = new ArrayList<>();

    public void updateTargetReceiptFiPoint(Integer targetReceiptFiPoint ){
        this.targetReceiptFiPoint = targetReceiptFiPoint;
    }
}