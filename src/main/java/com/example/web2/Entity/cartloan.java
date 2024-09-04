package com.example.web2.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cartloan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class cartloan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private librarycard libraryCard;
    @Column(name="amount", nullable = false)
    private int amount;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<cartloan_detail> loanDetails;
}
