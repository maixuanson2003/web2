package com.example.web2.Service;

import com.example.web2.DTO.response.ConversationResponse;

import java.util.List;

public interface ConversationService {
    public void CreateConversation(String token);
    public ConversationResponse findConversationById(Long id);
    public List<ConversationResponse> findAllConversation(Long id);
    public void DeleteConversationById(Long id);
}
