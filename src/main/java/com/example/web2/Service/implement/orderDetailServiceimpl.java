package com.example.web2.Service.implement;

import com.example.web2.Entity.*;
import com.example.web2.Repository.*;
import com.example.web2.Service.orderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class orderDetailServiceimpl implements orderDetailService {

    @Autowired
    private cartloanRepository cartloanRepository;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private bookRepository bookRepository;
    @Autowired
    private userOrderRepository userOrderRepository;
    @Autowired
    private orderDetailsRepository orderDetailsRepository;
    @Override
    public void createOrderDetailsByRequest(Integer bookId, Integer amount,Integer orderID){

      userorder userorder=userOrderRepository.findById( orderID).orElseThrow(()->new RuntimeException("not found"));
       book book=bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("not found"));
       orders_details ordersDetails=new orders_details().builder()
               .userorder(userorder)
               .book(book)
               .amountbook(amount)
               .returnDate(LocalDate.now().plusMonths(6).toString())
               .dateline(LocalDate.now().plusDays(7).toString())
               .build();
       orderDetailsRepository.save(ordersDetails);
    }
    @Override
    public void createOrderDetailsByCart(Integer cartloanid,Integer orderID){
        cartloan cartloan=cartloanRepository.findById(cartloanid).orElseThrow(()->new RuntimeException("not found"));
        userorder userorder=userOrderRepository.findById( orderID).orElseThrow(()->new RuntimeException("not found"));
        List<cartloan_detail> cartloanDetailList=cartloan.getLoanDetails();
        for (cartloan_detail cartloanDetail:cartloanDetailList){
            orders_details ordersDetails=new orders_details().builder()
                    .dateline(LocalDate.now().plusDays(7).toString())
                    .book(cartloanDetail.getBook())
                    .amountbook(cartloanDetail.getAmountBook())
                    .returnDate(LocalDate.now().plusMonths(6).toString())
                    .userorder(userorder)
                    .build();
            orderDetailsRepository.save(ordersDetails);
        }
    }

}
