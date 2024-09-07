package com.example.web2.Service;

import com.example.web2.DTO.response.ConversationResponse;

import java.util.List;

public interface ConversationService {
    public void CreateConversation(String token);
    public ConversationResponse findConversationById(Long id,String token);
    public ConversationResponse findConversationByUserName(String name);
    public List<ConversationResponse> findAllConversation(String token);
    public void DeleteConversationById(Long id);
}
