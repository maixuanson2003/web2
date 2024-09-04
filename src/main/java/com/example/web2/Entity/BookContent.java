package com.example.web2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name="BookContent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long contentId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private book book;

    @Column(name = "chapter_number")
    private Integer chapterNumber;
    @Column(name="page_number")
    private Integer page_number;

    @Column(name = "chapter_title", length = 255)
    private String chapterTitle;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
