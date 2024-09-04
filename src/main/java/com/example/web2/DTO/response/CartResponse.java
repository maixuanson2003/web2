package com.example.web2.DTO.response;
import com.example.web2.Entity.book;
import com.example.web2.Entity.cartloan_detail;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private String namebooks;
    private String author;
    private int amountBook;
}
