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
    public void createConversation(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        conversationService.CreateConversation(tokens);
    }

    // Tìm cuộc trò chuyện theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponse> findConversationById(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        ConversationResponse conversationResponse = conversationService.findConversationById(id,tokens);
        return ResponseEntity.ok(conversationResponse);
    }

    // Tìm tất cả các cuộc trò chuyện của một người dùng
    @GetMapping("/user")
    public ResponseEntity<List<ConversationResponse>> findAllConversations(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        List<ConversationResponse> conversationResponses = conversationService.findAllConversation(tokens);
        return ResponseEntity.ok(conversationResponses);
    }
    @GetMapping("/user/username")
    public ResponseEntity<ConversationResponse> findConversationByUserName(@RequestParam(name="username") String username) {

        ConversationResponse conversationResponses = conversationService.findConversationByUserName(username);
        return ResponseEntity.ok(conversationResponses);
    }

    // Xóa cuộc trò chuyện theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable Long id) {
        conversationService.DeleteConversationById(id);
        return ResponseEntity.ok().build();
    }
}

