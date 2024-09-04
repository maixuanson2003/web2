package com.example.web2.DTO.request;
import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRequest {
    private String title;
    private String author;
    private String publisher;
    private String description;
    private Integer totalpages ;
    private String category;
    private  MultipartFile  image;
}
