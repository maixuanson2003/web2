package com.example.web2.DTO.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chatinfor {
    private Long Messageid;
    private String userSender;
    private String Content;
    private String userRceive;
}
