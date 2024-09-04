package com.example.web2.Service.implement;

import com.example.web2.DTO.request.CartRequest;
import com.example.web2.DTO.response.CartResponse;
import com.example.web2.Entity.actor;
import com.example.web2.Entity.cartloan;
import com.example.web2.Entity.librarycard;
import com.example.web2.Entity.cartloan_detail;
import com.example.web2.Repository.cartloanRepository;
import com.example.web2.Repository.cartloanDetailsRepository;
import com.example.web2.Repository.actorRepository;
import com.example.web2.Repository.librarycardRepository;
import com.example.web2.Service.CartLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class cartloanServiceimpl implements CartLoanService {
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private librarycardRepository librarycardRepository;
    @Autowired
    private cartloanDetailsRepository cartloanDetailsRepository;
    @Autowired
    private cartloanRepository cartloanRepository;
    @Autowired
    private CartLoanDetailsServiceimpl cartLoanDetailsServiceimpl;
    @Override
    public void addproductToCart(CartRequest cartRequest, String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        librarycard librarycard=librarycardRepository.findById(actors.getLibrary_card().getId()).orElseThrow(()->new RuntimeException("not found"));
        if(librarycard.getStatus().equals("con han")){
            cartloan cartloan=cartloanRepository.findById(actors.getLibrary_card().getCartloan().getId()).orElseThrow(()->new RuntimeException("not found"));

            cartLoanDetailsServiceimpl.createCartDetails(cartRequest,token);
            List<cartloan_detail> cartloanDetailList=cartloanDetailsRepository.findAll();
            int totalAmount=0;
            for (cartloan_detail cartloanDetail:cartloanDetailList){
                totalAmount+=cartloanDetail.getAmountBook();
            }
            cartloan.setAmount(totalAmount);
            cartloanRepository.save(cartloan);
        }else {
            throw new RuntimeException("Library card has expired or is not valid. Please renew your library card.");
        }

    }
    @Override
    public void CreateCart(String token){
        Jwt jwt=jwtDecoder.decode(token);

        Long useridLong = jwt.getClaim("userid"); // Lấy giá trị dưới dạng Long
        Integer userid = useridLong.intValue();
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        cartloan cartloan=new cartloan().builder()
                .amount(0)
                .libraryCard(actors.getLibrary_card())
                .build();
        cartloanRepository.save(cartloan);
    }

    @Override
    @Transactional
    public String  CancelCart(int id,String token){
        Jwt jwt=jwtDecoder.decode(token);
        Integer userid=jwt.getClaim("userid");
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        cartloan cartloan=cartloanRepository.findById(actors.getLibrary_card().getCartloan().getId()).orElseThrow(()->new RuntimeException("not found"));
        List<cartloan_detail> cartloanDetailList=cartloan.getLoanDetails();
        for (cartloan_detail cartloanDetail:cartloanDetailList){
            cartloanDetailsRepository.deleteById(cartloanDetail.getId());
        }
        if (cartloanDetailList.isEmpty()){
            return "ok";
        }else {
            throw new RuntimeException("failed");
        }
    }
    @Override
    public List<CartResponse> getCartByLibraryCardId(String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid"); // Lấy giá trị dưới dạng Long
        Integer userid = useridLong.intValue();
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        cartloan cartloan=cartloanRepository.findById(actors.getLibrary_card().getCartloan().getId()).orElseThrow(()->new RuntimeException("not found"));
        List<cartloan_detail> cartloanDetailList=cartloan.getLoanDetails();
        List<CartResponse> cartResponseList=new ArrayList<>();
        int totalAmount=0;
        for (cartloan_detail cartloanDetail:cartloanDetailList){
            CartResponse cartResponse=new CartResponse().builder()
                    .namebooks(cartloanDetail.getBook().getTitle())
                    .author(cartloanDetail.getBook().getAuthor())
                    .amountBook(cartloanDetail.getAmountBook())
                    .build();
            cartResponseList.add(cartResponse);
        }

        return  cartResponseList;
    }

}
