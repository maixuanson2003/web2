package com.example.web2.DTO.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class libraryCardResponse {
    private String fullname;
    private String creatat;
    private String expiryDate;
    private String status;
    private String cardnumber;
    private String birthday;
    private String address;
    private byte[] image;

}
