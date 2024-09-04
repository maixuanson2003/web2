package com.example.web2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name="preview")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class preview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private book book;

    @Column(name = "Description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "create_at")
    private String createAt;

    @Column(name="rate")
    private int rate;

}
