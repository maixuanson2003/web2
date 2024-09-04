package com.example.web2.Controller;

import com.example.web2.DTO.request.actorRequest;
import com.example.web2.DTO.response.actorResponse;
import com.example.web2.Service.implement.actorServiecimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class actorController {
    @Autowired
    private actorServiecimpl actorService;


    @GetMapping
    public ResponseEntity<List<actorResponse>> getAllActors() {
        List<actorResponse> actors = actorService.getAllActor();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<actorResponse> getActorById(@PathVariable int id) {
        actorResponse actor = actorService.getActorById(id);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<actorResponse> updateActor(@PathVariable int id, @RequestBody actorRequest actorRequest) {
        actorResponse updatedActor = actorService.UpdateActor(actorRequest, id);
        return new ResponseEntity<>(updatedActor, HttpStatus.OK);
    }

    // Tạo mới một diễn viên
    @PostMapping
    public ResponseEntity<String> createActor(@RequestBody actorRequest actorRequest) {
        String result = actorService.createActor(actorRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Xóa diễn viên theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable int id) {
        boolean isDeleted = actorService.DeleteActorById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Actor deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Actor not found", HttpStatus.NOT_FOUND);
        }
    }
}
