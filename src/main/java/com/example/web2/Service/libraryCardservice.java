package com.example.web2.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.web2.DTO.response.libraryCardResponse;

import java.io.IOException;
import java.util.List;

public interface libraryCardservice {
    public String registryLibraryCard(String full_Name,String birthDay,String phone,String Address, MultipartFile File) throws IOException;
    public libraryCardResponse getCardDetailsByActorId(int actorid) throws IOException;
    public String deleteCardById(int id);
    public List<libraryCardResponse> getAllLibraryCard() throws IOException;

}
