package com.example.web2.Entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="actor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="username",nullable = false)
    private String username;
    @Column(name=" password",nullable = false)
    private String password;
    @Column(name="full_name")
    private String full_name;
    @Column(name="phone_number")
    private String phone_number;
    @Column(name=" address")
    private String address;
    @Column(name=" email")
    private String email;
    @Column(name="birthday")
    private String birthday;
    @Column(name="type")
    private String Type;
    @OneToOne(mappedBy = "actors",cascade = CascadeType.ALL, orphanRemoval = true)
    private librarycard library_card;
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
    private List<Conversations> Conservations;

}
