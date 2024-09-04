package com.example.web2.Controller;
import  com.example.web2.DTO.response.libraryCardResponse;
import com.example.web2.Service.libraryCardservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/librarycard")
public class libraryCardController {

    @Autowired
    private libraryCardservice libraryCardservice;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<String> registerLibraryCard(@RequestParam("full_Name") String fullName,
                                                      @RequestParam("birthDay") String birthDay,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("Address") String address,
                                                      @RequestPart("file") MultipartFile file
) {

        try {
            String response = libraryCardservice.registryLibraryCard(fullName,birthDay,phone,address, file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<libraryCardResponse> getCardById(@PathVariable int id) throws IOException{
        libraryCardResponse libraryCardResponse=libraryCardservice.getCardDetailsByActorId(id);
      return new ResponseEntity<>(libraryCardResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable int id) {
        try {
            String response = libraryCardservice.deleteCardById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint để lấy tất cả thẻ thư viện
    @GetMapping
    public ResponseEntity<List<libraryCardResponse>> getAllLibraryCard() {
        try {
            List<libraryCardResponse> libraryCardResponses = libraryCardservice.getAllLibraryCard();
            return new ResponseEntity<>(libraryCardResponses, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}