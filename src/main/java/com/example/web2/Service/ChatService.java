package com.example.web2.Service;

import com.example.web2.DTO.request.ChatRequest;
import com.example.web2.DTO.response.Chatinfor;
import com.example.web2.Entity.Chat;

import java.util.List;

public interface ChatService {
    public ChatRequest SendMessage(ChatRequest message,String token);
    public List<Chatinfor> GetMessageByconversations(String token, Long ConversationId);
    public void DeleteMessageByid(Long id);
}
