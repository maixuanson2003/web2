package com.example.web2.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="cartloan_detail ")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class cartloan_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private cartloan loan;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private book book;
    @Column(name = "amount_book", nullable = false)
    private Integer amountBook;
    @Column(name = "return_date")
    private String returnDate;
}
