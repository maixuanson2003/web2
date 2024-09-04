package com.example.web2.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="userorder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class userorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "librarycard_id",nullable = false)
    private librarycard librarycards;
    @Column(name="orderDate")
    private String orderDate;
    @Column(name="totalAmount")
    private double totalAmount;
    @Column(name="status")
    private String status;
    @Column(name = "time_create", nullable = false)
    private String timeCreate;
    @ManyToOne
    @JoinColumn(name="typetakeId",nullable = false)
    private typetake typeTake;
    @OneToMany(mappedBy = "userorder", cascade = CascadeType.ALL)
    private List<orders_details> orderDetails;
}
