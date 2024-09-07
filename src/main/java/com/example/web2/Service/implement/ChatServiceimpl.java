package com.example.web2.Service.implement;

import com.example.web2.DTO.request.ChatRequest;
import com.example.web2.DTO.response.Chatinfor;
import com.example.web2.Entity.Chat;
import com.example.web2.Entity.Conversations;
import com.example.web2.Entity.actor;
import com.example.web2.Repository.ChatRepository;
import com.example.web2.Repository.ConversationRepository;
import com.example.web2.Repository.actorRepository;
import com.example.web2.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceimpl implements ChatService {
    @Autowired
    private final   SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private ConversationRepository conversationRepository;

    public ChatServiceimpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    private actor findByName(String name){
        List<actor> actors=actorRepository.findAll();
        for (actor actor:actors){
            if (actor.getUsername().equals(name)){
                return actor;
            }
        }
        return null;
    }
    @Override
    public ChatRequest SendMessage(ChatRequest message,String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("notfound"));
        Conversations conversations=conversationRepository.findById(message.getConversationId()).orElseThrow(()->new RuntimeException("sss"));
        Chat chat=new Chat().builder()
                .actors(actors)
                .Content(message.getContent())
                .userSend(message.getUserSender())
                .Conversation(conversations)
                .create_at(LocalDate.now().toString())
                .userRceive(message.getUserRceive())
                .build();
        chatRepository.save(chat);
        return message;
    }
    @Override
    public List<Chatinfor> GetMessageByconversations(String token, Long ConversationId){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        List<Chatinfor> chatinforList=new ArrayList<>();
        actor actor=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("notfound"));
        List<Conversations> conversationsList=actor.getConservations();
        for (Conversations conversations:conversationsList){
            if (conversations.getId()==ConversationId){
                List<Chat> chatList=conversations.getChats();
                for (Chat chat:chatList){
                    Chatinfor chatinfor=new Chatinfor().builder()
                            .Messageid(chat.getId())
                            .userSender(chat.getUserSend())
                            .Content(chat.getContent())
                            .userRceive(chat.getUserRceive())
                            .build();
                    chatinforList.add(chatinfor);
                }
                break;
            }
        }
        return chatinforList;
    }
    @Override
    public void DeleteMessageByid(Long id){
        chatRepository.deleteById(id);
    }
}
