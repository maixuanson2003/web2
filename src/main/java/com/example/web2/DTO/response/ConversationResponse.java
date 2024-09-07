package com.example.web2.DTO.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConversationResponse {
    private Long id;
    private String nameUserRecevive;
    private  List<Chatinfor> chatinfors;
}
