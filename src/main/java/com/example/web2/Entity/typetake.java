package com.example.web2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="typetake")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class typetake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "typeTake",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userorder> userorders;
    @Column(name="nametype")
    private String nametype;
}
