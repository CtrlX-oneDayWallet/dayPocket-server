package hyu.dayPocket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private String location;

    private String receiptItem;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberChallenge_id")
    private MemberChallenge memberChallenge;

    public static Receipt receiptFrom(String location, String receiptItem, Integer price){
        Receipt receipt = new Receipt();
        receipt.location = location;
        receipt.receiptItem = receiptItem;
        receipt.price = price;
        return receipt;
    }


}
