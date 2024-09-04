package com.example.web2.Service;

import com.example.web2.DTO.response.actorResponse;
import com.example.web2.DTO.request.actorRequest;

import java.util.List;

public interface actorService {
    public List<actorResponse> getAllActor();
    public actorResponse getActorById(int id);
    public actorResponse UpdateActor(actorRequest actorRequests,int id);
    public String createActor(actorRequest actorRequests);
    public boolean DeleteActorById(int id);

}
