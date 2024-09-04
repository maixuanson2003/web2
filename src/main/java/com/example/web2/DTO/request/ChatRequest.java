package com.example.web2.DTO.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatRequest {
    private Long ConversationId;
    private String userSender;
    private String Content;
    private String userRceive;
}
