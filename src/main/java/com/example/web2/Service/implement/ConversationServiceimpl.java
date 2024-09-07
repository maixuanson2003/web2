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
        System.out.println("Received Token: " + token);
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        boolean CheckCOnversationExists=false;
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        List<Conversations> conversationOfActors=actors.getConservations();
        List<Conversations> conversations=conversationRepository.findAll();
        int result=0;
        for (Conversations conversationactor:conversationOfActors){
            for (Conversations conversations1:conversations){
                if (conversationactor.getId()==conversations1.getId()){
                    result++;
                    break;
                }
            }
        }
        if (result==conversationOfActors.size()){
            CheckCOnversationExists=true;
        }

        if (!CheckCOnversationExists|| conversationOfActors.size()==0){
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
    public ConversationResponse findConversationByUserName(String name){
        List<Conversations> conversationsList=conversationRepository.findAll();
        boolean Check=false;
        for (Conversations conversations:conversationsList){
            List<actor> actors=conversations.getActors();
            String namUserReCevie="";
            for (actor actor:actors){
                if (actor.getUsername().equals(name)) {
                    Check=true;
                    namUserReCevie=actor.getUsername();
                }
            }
          if (Check){
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
                      .nameUserRecevive(namUserReCevie)
                      .chatinfors(chatinforList)
                      .build();
              return conversationResponse ;
          }
        }
        return null;
    }

    @Override
    public ConversationResponse findConversationById(Long id,String token) {
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actor=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
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
        String nameUserReCevie="";
        List<actor> actorList=conversations.getActors();
        for (actor actors:actorList){
            if (!actors.getUsername().equals(actor.getUsername())){
                nameUserReCevie=actors.getUsername();
            }
        }
        ConversationResponse conversationResponse =new ConversationResponse().builder()
                .id(conversations.getId())
                .nameUserRecevive(nameUserReCevie)
                .chatinfors(chatinforList)
                .build();
        return conversationResponse ;
    }
    @Override
    public List<ConversationResponse> findAllConversation(String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actor=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        List<Conversations> conversationsList=actor.getConservations();
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
            String nameUserReCevie="";
            List<actor> actorList=conversations.getActors();
            for (actor actors:actorList){
                if (!actors.getUsername().equals(actor.getUsername())){
                    nameUserReCevie=actors.getUsername();
                    break;
                }
            }
            ConversationResponse conversationResponse =new ConversationResponse().builder()
                    .id(conversations.getId())
                    .nameUserRecevive(nameUserReCevie)
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
