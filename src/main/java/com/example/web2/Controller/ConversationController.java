package com.example.web2.Controller;

import com.example.web2.DTO.response.ConversationResponse;
import com.example.web2.Service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    // Tạo một cuộc trò chuyện mới
    @PostMapping("/create")
    public ResponseEntity<Void> createConversation(@RequestHeader("Authorization") String token) {
        conversationService.CreateConversation(token);
        return ResponseEntity.ok().build();
    }

    // Tìm cuộc trò chuyện theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponse> findConversationById(@PathVariable Long id) {
        ConversationResponse conversationResponse = conversationService.findConversationById(id);
        return ResponseEntity.ok(conversationResponse);
    }

    // Tìm tất cả các cuộc trò chuyện của một người dùng
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ConversationResponse>> findAllConversations(@PathVariable Long id) {
        List<ConversationResponse> conversationResponses = conversationService.findAllConversation(id);
        return ResponseEntity.ok(conversationResponses);
    }

    // Xóa cuộc trò chuyện theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable Long id) {
        conversationService.DeleteConversationById(id);
        return ResponseEntity.ok().build();
    }
}

