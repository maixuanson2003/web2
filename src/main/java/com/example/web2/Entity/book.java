package com.example.web2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;


@Entity
@Table(name="book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String author;

    @Column(length = 255)
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String description;



    @Column(name = "total_pages")
    private Integer totalpages ;
    @Column(name = "amount")
    private Integer amount ;

    @Column(name="category")
    private String category;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "created_at", updatable = false, nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookContent> bookcontent;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<preview> previews;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<cartloan_detail> cartloanDetails;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<orders_details> orders_details;
}
