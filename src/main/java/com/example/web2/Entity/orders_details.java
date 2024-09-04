package com.example.web2.Entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders_details")
public class orders_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "orderid",nullable = false)
    private userorder userorder;

    @ManyToOne
    @JoinColumn(name = "bookid", nullable = false)
    private book book;
    @Column(name="amountbook")
    private int amountbook;
    @Column(name = "return_date")
    private String returnDate;
    @Column(name = "dateline")
    private String dateline;
}
