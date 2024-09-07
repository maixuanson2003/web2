package com.example.web2.Controller;

import com.example.web2.DTO.request.ChatRequest;
import com.example.web2.DTO.response.Chatinfor;
import com.example.web2.Entity.Chat;
import com.example.web2.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController

public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatService chatService;
    @PostMapping("/sendMessage")
    public ChatRequest  sendMessage(@RequestBody ChatRequest message, @RequestHeader("Authorization") String token){
        String tokens = token.replace("Bearer ", "");
        return chatService.SendMessage(message,tokens);
    }
    @MessageMapping("/chat/Conversation/{ConversationId}")
    public void sendGroupMessage(@DestinationVariable Integer ConversationId, ChatRequest message) {
        // Send message to all group members
        messagingTemplate.convertAndSend("/Topic/Conversation/" +  ConversationId.toString(), message);
    }

    @GetMapping("/conversations")
    public List<Chatinfor> getMessagesByConversation(@RequestHeader("Authorization") String token, @PathVariable Long ConversationId) {
        String tokens = token.replace("Bearer ", "");
        return chatService.GetMessageByconversations(tokens, ConversationId);
    }

    // Xóa tin nhắn theo ID
    @DeleteMapping("/delete/{id}")
    public void deleteMessageById(@PathVariable Long id) {
        chatService.DeleteMessageByid(id);
    }
}
