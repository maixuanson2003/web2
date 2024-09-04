package com.example.web2.DTO.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookResponse {
    private String title;


    private String author;


    private String publisher;


    private String description;




    private Integer totalpages ;


    private String category;



    private byte[] image;
}
