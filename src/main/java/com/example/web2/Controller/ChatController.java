package com.example.web2.Controller;

import com.example.web2.DTO.request.ChatRequest;
import com.example.web2.DTO.response.Chatinfor;
import com.example.web2.Entity.Chat;
import com.example.web2.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;
    @MessageMapping("/Conversation")
    public void  sendMessage(ChatRequest message, @RequestHeader("Authorization") String token){
        chatService.SendMessage(message,token);
    }
    @GetMapping("/conversations")
    public List<Chatinfor> getMessagesByConversation(@RequestHeader("Authorization") String token, @PathVariable Long ConversationId) {
        return chatService.GetMessageByconversations(token, ConversationId);
    }

    // Xóa tin nhắn theo ID
    @DeleteMapping("/delete/{id}")
    public void deleteMessageById(@PathVariable Long id) {
        chatService.DeleteMessageByid(id);
    }
}
