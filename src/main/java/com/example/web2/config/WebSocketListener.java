package com.example.web2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketListener.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    public void handleDisconnect(SessionConnectedEvent event){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String)headerAccessor.getSessionAttributes().get("username");
        if (username!=null){

        }
    }

}
