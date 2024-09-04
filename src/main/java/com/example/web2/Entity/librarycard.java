package com.example.web2.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="librarycard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class librarycard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "actor_id",unique = true)
    private actor actors;
    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "avatar", nullable = false)
    private String avatar;
    @Column(name="status")
    private String status;
    @OneToOne(mappedBy = "libraryCard",cascade = CascadeType.ALL, orphanRemoval = true)
    private cartloan cartloan;
    @OneToMany(mappedBy = "librarycards",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userorder> userorders;

}
