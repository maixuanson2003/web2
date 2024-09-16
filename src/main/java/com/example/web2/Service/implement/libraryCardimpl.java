package com.example.web2.Service.implement;
import com.example.web2.DTO.response.libraryCardResponse;
import com.example.web2.Entity.actor;
import com.example.web2.Enums.LibraryCardEnums;
import com.example.web2.Service.libraryCardservice;
import com.example.web2.Repository.librarycardRepository;
import com.example.web2.Service.actorService;
import com.example.web2.Entity.librarycard;
import com.example.web2.Repository.actorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.web2.Service.implement.EmailServiceimpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class libraryCardimpl implements libraryCardservice  {
    @Autowired
    private librarycardRepository librarycardRepository;
    @Autowired
    private actorService actorService;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private EmailServiceimpl emailServiceimpl;

    private final String PATH_FILE = "D:\\web2\\src\\main\\java\\assetAvatar";

    private boolean CheckLibraryCard(librarycard libraryCardDetails){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkDate=LocalDate.parse(libraryCardDetails.getExpiryDate(),formatter);
        if(today.isAfter(checkDate)){
            return true;
        }else {
            return false;
        }
    }
    private actor findByFullName(String fullname){
        List<actor> actorList=actorRepository.findAll();
        for (actor actors:actorList){
            if(actors.getFull_name().equals(fullname)){
                return actors;
            }
        }
        throw new RuntimeException("Actor with full name '" + fullname + "' not found.");
    }

    @Override
    public String registryLibraryCard(String full_Name,String birthDay,String phone,String Address, MultipartFile File) throws IOException {
        actor actors=findByFullName(full_Name);
        List<librarycard> listlibrarycard=librarycardRepository.findAll();
        int currentYear = LocalDate.now().getYear();
        String cardnumber;
        System.out.println(listlibrarycard.size());
        if(!listlibrarycard.isEmpty()){
            int number=listlibrarycard.size()+1;
            cardnumber=currentYear+""+number;
        }else{
            cardnumber = currentYear + "" + 1;
        }
        Path uploadPath = Paths.get(PATH_FILE);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" +  File.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        File.transferTo(filePath.toFile());
        if(actorService.getActorById(actors.getId()).getStatusCard().equals("dont have librarycard")){
            librarycard librarycard=new librarycard().builder()
                    .actors(actors)
                    .detail("ok")
                    .cardNumber(cardnumber)
                    .createAt(LocalDate.now().toString())
                    .status("con han")
                    .expiryDate(LocalDate.now().plusYears(4).toString())
                    .avatar(fileName)
                    .build();
            librarycardRepository.save(librarycard);
            List<librarycard> listlibraryCard=librarycardRepository.findAll();
            if(listlibraryCard.isEmpty()){
                throw new RuntimeException("error");
            }else {
                return "success";
            }
        }else {
            return "you already have libraryCard";
        }
    }
    @Override
    public libraryCardResponse getCardDetailsByActorId(int actorid) throws IOException {
        List<librarycard> librarycardList=librarycardRepository.findAll();
        for (librarycard librarycard:librarycardList){
            if (librarycard.getActors().getId()==actorid){
                Path imagePath = Paths.get(PATH_FILE, librarycard.getAvatar());
                byte[] imageBytes = Files.readAllBytes(imagePath);
                return  libraryCardResponse.builder()
                        .fullname(librarycard.getActors().getFull_name())
                        .creatat(librarycard.getCreateAt())
                        .image(imageBytes)
                        .birthday(librarycard.getActors().getBirthday())
                        .address(librarycard.getActors().getAddress())
                        .status(librarycard.getStatus())
                        .cardnumber(librarycard.getCardNumber())
                        .expiryDate(librarycard.getExpiryDate())
                        .build();
            }else {
                throw new RuntimeException("dont have libraryCard");
            }
        }
        return null;
    }
    @Override
    public String deleteCardById(int id){
        try {
            if (librarycardRepository.existsById(id)) {
                librarycardRepository.deleteById(id);
                // Xác nhận xóa thành công
                if (!librarycardRepository.existsById(id)) {
                    return "Delete success";
                } else {
                    return "Failed to delete card";
                }
            } else {
                return "Card not found";
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ khi xóa không thành công.
            return "Failed to delete card: " + e.getMessage();
        }
    }
    @Override
    public List<libraryCardResponse> getAllLibraryCard() throws IOException {
        List<librarycard> librarycardList=librarycardRepository.findAll();
        List<libraryCardResponse> libraryCardResponseList=new ArrayList<>();
        for (librarycard librarycard:librarycardList){
            Path imagePath = Paths.get(PATH_FILE, librarycard.getAvatar());
            byte[] imageBytes = Files.readAllBytes(imagePath);
            libraryCardResponse libraryCardResponse=new libraryCardResponse().builder()
                    .fullname(librarycard.getActors().getFull_name())
                    .image(imageBytes)
                    .creatat(librarycard.getCreateAt())
                    .birthday(librarycard.getActors().getBirthday())
                    .address(librarycard.getActors().getAddress())
                    .status(librarycard.getStatus())
                    .cardnumber(librarycard.getCardNumber())
                    .expiryDate(librarycard.getExpiryDate())
                    .build();
            libraryCardResponseList.add(libraryCardResponse);
        }
        return libraryCardResponseList;
    }
    @Override
    public void  libraryCardExtension(String full_Name,String birthDay,String phone,String Address,String cardNumber){
        actor actors=findByFullName(full_Name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate CheckBirthDay=LocalDate.parse( birthDay,formatter);
        LocalDate actorOfBirthDay=LocalDate.parse( actors.getBirthday(),formatter);
        boolean CheckUserProfileCard=false;

        if (CheckBirthDay.isEqual(actorOfBirthDay)&&actors.getPhone_number().equals(phone)&&actors.getLibrary_card().getCardNumber().equals(cardNumber)){
            CheckUserProfileCard=true;
        }
        if (CheckUserProfileCard){
            librarycard librarycard=librarycardRepository.findById(actors.getLibrary_card().getId()).orElseThrow(()->new RuntimeException("Card not found"));
            LocalDate ExpiryDate=LocalDate.parse(librarycard.getExpiryDate(),formatter);
            LocalDate Date=LocalDate.now();
            long daysBetween= ChronoUnit.DAYS.between(Date,ExpiryDate);
            if (librarycard.getStatus().equals(LibraryCardEnums.HET_HAN.getMessage())|| daysBetween<=30){
                librarycard.setExpiryDate(Date.plusMonths(6).toString());
                if (librarycard.getStatus().equals(LibraryCardEnums.HET_HAN.getMessage())){
                    librarycard.setStatus(LibraryCardEnums.CON_HAN.getMessage());
                }
            }
            librarycardRepository.save(librarycard);
            String userEmail = librarycard.getActors().getEmail();
            String message = "Bạn đã gia hạn thành Công thẻ thư viện";
            emailServiceimpl.sendEmail(userEmail, "Thông báo Gia hạn thẻ thành Công", message);

        }else {
            throw new RuntimeException("cardInfo not match");
        }
    }
    @Scheduled(fixedDelay = 60000)
    public void CheckDatelibrary(){
        List<librarycard> librarycardList=librarycardRepository.findAll();
        for(librarycard librarycard:librarycardList){
            if (CheckLibraryCard(librarycard)&&librarycard.getStatus().equals("con han")){
                String userEmail = librarycard.getActors().getEmail(); // card.getUser() cần được định nghĩa trong entity LibraryCard
                String message = "Thẻ thư viện của bạn da hết hạn";
                emailServiceimpl.sendEmail(userEmail, "Thông báo thẻ thư viện hết hạn", message);
                librarycard.setStatus("het han");
                librarycardRepository.save(librarycard);
            }
        }
    }
}
