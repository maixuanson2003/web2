package com.example.web2.Service.implement;
import com.example.web2.DTO.request.actorRequest;
import com.example.web2.DTO.response.actorResponse;
import com.example.web2.Repository.actorRepository;
import  com.example.web2.Service.actorService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.web2.Entity.actor;
import com.example.web2.Repository.librarycardRepository;
import com.example.web2.Entity.librarycard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class actorServiecimpl implements actorService {
    @Autowired
    private actorRepository actorrepository;
    @Autowired
    private librarycardRepository librarycardrepository;
    @Override
    public List<actorResponse> getAllActor(){
       List<actor> actors=actorrepository.findAll();
       List<actorResponse> actorres=new ArrayList<>();
       for(actor actor1: actors){
           actorResponse actorResponse=new actorResponse();
           actorResponse.setId(actor1.getId());
           actorResponse.setFull_name(actor1.getFull_name());
           actorResponse.setAddress(actor1.getAddress());
           actorResponse.setBirthday(actor1.getBirthday());
           actorResponse.setPhone_number(actor1.getPhone_number());
           boolean CheckTrue=false;
           List<librarycard> listlibrarycard=librarycardrepository.findAll();
           for (librarycard librarycard:listlibrarycard){
               if(librarycard.getActors().getId()==actor1.getId()){
                   CheckTrue=true;
                   break;
               }
           }
           if(CheckTrue==true){
               actorResponse.setStatusCard("already have librarycard");
           }else{
               actorResponse.setStatusCard("dont have librarycard");
           }

           actorres.add(actorResponse);
       }
       return  actorres;
    }
    @Override
    public actorResponse getActorById(int id){
        actor actor=actorrepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        actorResponse actorres=new actorResponse();
        actorres.setId(actor.getId());
        actorres.setFull_name(actor.getFull_name());
        actorres.setPhone_number(actorres.getPhone_number());
        actorres.setBirthday(actorres.getBirthday());
        boolean CheckTrue=false;
        List<librarycard> listlibrarycard=librarycardrepository.findAll();
        for (librarycard librarycard:listlibrarycard){
            if(librarycard.getActors().getId()==id){
                CheckTrue=true;
                break;
            }
        }
        if(CheckTrue==true){
            actorres.setStatusCard("already have librarycard");
        }else{
            actorres.setStatusCard("dont have librarycard");
        }
        actorres.setAddress(actorres.getAddress());
        return  actorres;

    }
    @Override
    @Transactional
    public actorResponse UpdateActor(actorRequest actorRequests, int id){
        actor actors=actorrepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        actors.setUsername(actorRequests.getUsername());
        actors.setPassword(passwordEncoder.encode(actorRequests.getPassword()));
        actors.setBirthday(actorRequests.getBirthday());
        actors.setAddress(actorRequests.getAddress());
        actors.setFull_name(actorRequests.getFull_name());
        actors.setPhone_number(actorRequests.getPhone_number());
        actors.setType(actorRequests.getType());
        actorrepository.save(actors);
        boolean CheckTrue=false;
        List<librarycard> listlibrarycard=librarycardrepository.findAll();
        for (librarycard librarycard:listlibrarycard){
            if(librarycard.getActors().getId()==id){
                CheckTrue=true;
                break;
            }
        }
        actorResponse actorResponse= new actorResponse().builder().
                address(actors.getAddress())
                .full_name(actors.getFull_name())
                .birthday(actors.getBirthday())
                .phone_number(actors.getPhone_number())
                .statusCard(CheckTrue==true? "already have librarycard":"dont have librarycard")
                .build();
        return  actorResponse;

    }
    @Override
    public String createActor(actorRequest actorRequests){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        actor saveactor=new actor();
        saveactor.setFull_name(actorRequests.getFull_name());
        saveactor.setBirthday(actorRequests.getBirthday());
        saveactor.setUsername(actorRequests.getUsername());
        saveactor.setEmail(actorRequests.getEmail());
        saveactor.setPassword(passwordEncoder.encode(actorRequests.getPassword()));
        saveactor.setPhone_number(actorRequests.getPhone_number());
        saveactor.setType(actorRequests.getType());
        actorrepository.save(saveactor);

        actor actorfind= actorrepository.findByUsername(actorRequests.getUsername());
        if(actorfind!=null){
            return "ok";

        }else {
            throw new RuntimeException("khong cap nhat thanh cong");

        }
    }
    @Override
    @Transactional
    public boolean DeleteActorById(int id){
        actor actors=actorrepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        if(!actors.getType().equals("ADMIN")){
            actorrepository.deleteById(id);

        }
        return !actorrepository.existsById(id);

    }
}
