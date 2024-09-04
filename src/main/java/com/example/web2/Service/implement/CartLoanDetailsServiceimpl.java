package com.example.web2.Service.implement;
import com.example.web2.DTO.request.CartRequest;
import com.example.web2.Service.cartLoanDetailService;
import com.example.web2.Repository.cartloanRepository;
import com.example.web2.Entity.book;
import com.example.web2.Entity.actor;
import com.example.web2.DTO.response.actorResponse;
import com.example.web2.Service.actorService;
import com.example.web2.Repository.actorRepository;
import com.example.web2.Entity.librarycard;
import com.example.web2.Entity.cartloan_detail;
import com.example.web2.Entity.cartloan;
import com.example.web2.Repository.librarycardRepository;
import com.example.web2.Repository.bookRepository;
import com.example.web2.Repository.cartloanDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartLoanDetailsServiceimpl implements cartLoanDetailService  {
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private actorService actorService;
    @Autowired
    private libraryCardimpl libraryCardimpl;
    @Autowired
    private librarycardRepository librarycardRepository;
    @Autowired
    private cartloanRepository cartloanRepository;
    @Autowired
    private bookRepository bookRepository;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private cartloanDetailsRepository cartloanDetailsRepository;
    private cartloan SearchCartLoan(int cardid){
        List<cartloan> cartloanList=cartloanRepository.findAll();
        for(cartloan cartloan:cartloanList){
            if (cartloan.getLibraryCard().getId()==cardid){
                return cartloan;
            }
        }
        return null;
    }
    private book SearchbookByname(String  name){
        List<book> bookList=bookRepository.findAll();
        for(book book:bookList){
            if (book.getTitle().equals(name)){
                return book;
            }
        }
        return null;
    }
    @Override
    @Transactional
    public cartloan_detail createCartDetails(CartRequest cartRequest,String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        System.out.println(userid.getClass());
        actor actor=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        actorResponse actorResponse=actorService.getActorById(userid);
        if(!actorResponse.getStatusCard().equals("dont have librarycard")){
        cartloan_detail cartloanDetail=new cartloan_detail().builder()
                .loan(actor.getLibrary_card().getCartloan())
                .amountBook(cartRequest.getAmount())
                .book(SearchbookByname(cartRequest.getBookname()))
                .returnDate(LocalDate.now().plusMonths(6).toString())
                .build();

        cartloanDetailsRepository.save(cartloanDetail);
        return  cartloanDetail;

        } else {
            throw new RuntimeException("dang ky the thu vien");
        }
    }
    @Override
    @Transactional
    public String DeleteCartDetailsById(int id){
        cartloanDetailsRepository.deleteById(id);
        return "Ok";
    }

}
