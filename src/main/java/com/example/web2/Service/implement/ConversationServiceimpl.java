package com.example.web2.Service.implement;

import com.example.web2.DTO.response.Chatinfor;
import com.example.web2.DTO.response.ConversationResponse;
import com.example.web2.Entity.Chat;
import com.example.web2.Entity.Conversations;
import com.example.web2.Entity.actor;
import com.example.web2.Repository.ConversationRepository;
import com.example.web2.Repository.actorRepository;
import com.example.web2.Service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ConversationServiceimpl implements ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private JwtDecoder jwtDecoder;
    private actor findActorByRoleAdmin(){
        List<actor> actorList=actorRepository.findAll();
        for (actor actor:actorList){
            if (actor.getType().equals("ADMIN")){
                return actor;
            }
        }
        return null;
    }

    @Override
    public void CreateConversation(String token) {
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        boolean CheckCOnversationExists=false;
        List<Conversations> conversations=conversationRepository.findAll();
        for (Conversations conversations1:conversations){
           List<actor> actors=conversations1.getActors();
           for (actor actor:actors){
               if (actor.getId()==userid && !actor.getType().equals("ADMIN")){
                   CheckCOnversationExists=true;
                   break;
               }
           }
        }
        if (!CheckCOnversationExists){
            List<actor> actorsAdd=new ArrayList<>();
            actor actor1=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found user"));
            actorsAdd.add(findActorByRoleAdmin());
            actorsAdd.add(actor1);
            Conversations NewConversation=new Conversations().builder()
                    .LocalDate(LocalDate.now().toString())
                    .actors(actorsAdd)
                    .build();
            conversationRepository.save(NewConversation);
        }
    }

    @Override
    public ConversationResponse findConversationById(Long id) {
        Conversations conversations=conversationRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        List<Chat> chatList=conversations.getChats();
        List<Chatinfor> chatinforList=new ArrayList<>();
        for (Chat chat:chatList){
            Chatinfor chatinfor=new Chatinfor().builder()
                    .Messageid(chat.getId())
                    .Content(chat.getContent())
                    .userRceive(chat.getUserRceive())
                    .userSender(chat.getUserSend())
                    .build();
            chatinforList.add(chatinfor);
        }
        ConversationResponse conversationResponse =new ConversationResponse().builder()
                .id(conversations.getId())
                .chatinfors(chatinforList)
                .build();
        return conversationResponse ;
    }
    @Override
    public List<ConversationResponse> findAllConversation(Long id){
        List<Conversations> conversationsList=conversationRepository.findAll();
        List<ConversationResponse> conversationResponseList =new ArrayList<>();
        for (Conversations conversations:conversationsList){
            List<Chat> chatList=conversations.getChats();
            List<Chatinfor> chatinforList=new ArrayList<>();
            for (Chat chat:chatList){
                Chatinfor chatinfor=new Chatinfor().builder()
                        .Messageid(chat.getId())
                        .Content(chat.getContent())
                        .userRceive(chat.getUserRceive())
                        .userSender(chat.getUserSend())
                        .build();
                chatinforList.add(chatinfor);

            }
            ConversationResponse conversationResponse =new ConversationResponse().builder()
                    .id(conversations.getId())
                    .chatinfors(chatinforList)
                    .build();
            conversationResponseList.add(conversationResponse);


        }
        return conversationResponseList;
    }


    @Override
    public void DeleteConversationById(Long id) {
        conversationRepository.deleteById(id);

    }
}
