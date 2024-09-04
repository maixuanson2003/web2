package com.example.web2.DTO.response;

import com.example.web2.Entity.orders_details;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class orderResponse {
    private int id;
    private String fullnameUser;
    private String address;
    private String status;
    private String typeTake;
    private String phoneNumber;
    private String Dateend;
    private List<BookResponse> Book;

}
