package com.example.web2.Service.implement;

import com.example.web2.DTO.response.BookResponse;
import com.example.web2.DTO.response.orderResponse;
import com.example.web2.Entity.actor;
import com.example.web2.Entity.orders_details;
import com.example.web2.Entity.cartloan;
import com.example.web2.Entity.userorder;
import com.example.web2.Entity.typetake;
import com.example.web2.Enums.EnumOrder;
import com.example.web2.Repository.typetakeRepository;
import com.example.web2.Repository.cartloanRepository;
import com.example.web2.Repository.actorRepository;
import com.example.web2.Repository.userOrderRepository;
import com.example.web2.Service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class orderServiceimpl implements orderService {
    @Autowired
    private userOrderRepository userOrderRepository;
    @Autowired
    private orderDetailServiceimpl orderDetailServiceimpl;
    @Autowired
    private typetakeRepository typetakeRepository;
    @Autowired
    private actorRepository actorRepository;
    @Autowired
    private cartloanRepository cartloanRepository;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Override
    public List<orderResponse> getAllOrderResponse(){
        List<userorder> userorderList=userOrderRepository.findAll();
        List<orderResponse> orderResponsesList=new ArrayList<>();
        for (userorder userorder:userorderList){
            List<BookResponse> bookResponseList=new ArrayList<>();
            List<orders_details> ordersDetailsList=userorder.getOrderDetails();
            for (orders_details ordersDetails:ordersDetailsList){
                BookResponse bookResponse=new BookResponse().builder()
                        .title(ordersDetails.getBook().getTitle())
                        .publisher(ordersDetails.getBook().getPublisher())
                        .image(ordersDetails.getBook().getImage().getBytes())
                        .totalpages(ordersDetails.getBook().getTotalpages())
                        .description(ordersDetails.getBook().getDescription())
                        .category(ordersDetails.getBook().getCategory())
                        .author(ordersDetails.getBook().getAuthor())
                        .build();
                bookResponseList.add(bookResponse);

            }
            orderResponse orderResponse=new orderResponse().builder()
                    .id(userorder.getId())
                    .status(userorder.getStatus())
                    .typeTake(userorder.getTypeTake().getNametype())
                    .address(userorder.getLibrarycards().getActors().getAddress())
                    .fullnameUser(userorder.getLibrarycards().getActors().getFull_name())
                    .phoneNumber(userorder.getLibrarycards().getActors().getPhone_number())
                    .Dateend(LocalDate.now().plusDays(7).toString())
                    .Book(bookResponseList)
                    .build();
            orderResponsesList.add(orderResponse);
        }
        return orderResponsesList;

    }
    private String processOrderDetails(boolean checkCartOrder, boolean checkBookOrder, Integer cartloanid, Integer bookid, Integer amount, userorder order) {
        if (checkCartOrder) {
            orderDetailServiceimpl.createOrderDetailsByCart(cartloanid, order.getId());
            cartloan cartloan=cartloanRepository.findById(cartloanid).orElseThrow(()->new RuntimeException("not found"));
            int totalAmount = cartloan.getAmount();
            order.setTotalAmount(totalAmount);
            userOrderRepository.save(order);
            return "ok";
        }

        if (checkBookOrder) {
            orderDetailServiceimpl.createOrderDetailsByRequest(bookid, amount, order.getId());
            if(order.getTotalAmount()!=0){
                order.setTotalAmount(order.getTotalAmount() + amount);
            }else {
                order.setTotalAmount( amount);
            }

            userOrderRepository.save(order);
            return "ok";
        }

        return null;
    }
    private boolean CheckOrder(userorder userorder,String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actors= actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        List<userorder> userorderList=actors.getLibrary_card().getUserorders();
        for (userorder userorder1:userorderList){
            if(userorder1.getId()==userorder.getId()||userorder1.getStatus().equals(EnumOrder.DA_LAY.getDescription())||userorder1.getStatus().equals("het han lay")){
                return true;
            }
        }
        return false;
    }
    private typetake findByName(String name){
        List<typetake> typetakeList=typetakeRepository.findAll();

        for (typetake typetake:typetakeList){
            if(typetake.getNametype().equals( name)){
                return  typetake;

            }
        }
        return null;
    }
    @Override
    @Transactional
    public String addOrder(Integer cartloanid,Integer bookid,Integer amount,String token,String typetakes){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actors= actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        boolean checkCartOrder=cartloanid!=null&&bookid==null&&amount==null;
        boolean CheckBookOrder=bookid!=null&&amount>=1&&cartloanid==null;
        boolean Check=true;
        List<userorder> userorder1=userOrderRepository.findAll();

        for (userorder userorder:userorder1){
            if (CheckOrder(userorder,token)){
                Check=false;
                break;
            }
        }
       if (Check){
           userorder userorders=new userorder().builder()
                   .orderDate(LocalDate.now().plusDays(7).toString())
                   .typeTake(findByName(typetakes))
                   .librarycards(actors.getLibrary_card())
                   .timeCreate(LocalDate.now().toString())
                   .totalAmount(0)
                   .status(EnumOrder.DANG_XU_LY.getDescription())
                   .build();
           userOrderRepository.save(userorders);

           return processOrderDetails(checkCartOrder, CheckBookOrder, cartloanid, bookid, amount, userorders);
       }else {
           for (userorder userorder:userorder1){
               if (CheckOrder(userorder,token)){
                   return processOrderDetails(checkCartOrder, CheckBookOrder, cartloanid, bookid, amount, userorder);
               }
           }
       }
       return  null;
    }
    @Override
    @Transactional
    public String deleteOrderById(int id){
        userOrderRepository.deleteById(id);
        return "ok";
    }
    @Override
    public List<orderResponse> getAllOrderForUser(String token){
        Jwt jwt=jwtDecoder.decode(token);
        Long useridLong = jwt.getClaim("userid");
        Integer userid = useridLong.intValue();
        actor actors=actorRepository.findById(userid).orElseThrow(()->new RuntimeException("not found"));
        List<userorder> userorderList=actors.getLibrary_card().getUserorders();
        List<orderResponse> orderResponsesList=new ArrayList<>();

        for (userorder userorder:userorderList){
            List<BookResponse> bookResponseList=new ArrayList<>();
            List<orders_details> ordersDetailsList=userorder.getOrderDetails();
            for (orders_details ordersDetails:ordersDetailsList){
                BookResponse bookResponse=new BookResponse().builder()
                        .title(ordersDetails.getBook().getTitle())
                        .publisher(ordersDetails.getBook().getPublisher())
                        .image(ordersDetails.getBook().getImage().getBytes())
                        .totalpages(ordersDetails.getBook().getTotalpages())
                        .description(ordersDetails.getBook().getDescription())
                        .category(ordersDetails.getBook().getCategory())
                        .author(ordersDetails.getBook().getAuthor())
                        .build();
                bookResponseList.add(bookResponse);

            }
            orderResponse orderResponse=new orderResponse().builder()
                    .id(userorder.getId())
                    .status(userorder.getStatus())
                    .typeTake(userorder.getTypeTake().getNametype())
                    .address(userorder.getLibrarycards().getActors().getAddress())
                    .fullnameUser(userorder.getLibrarycards().getActors().getFull_name())
                    .phoneNumber(userorder.getLibrarycards().getActors().getPhone_number())
                    .Dateend(LocalDate.now().plusDays(7).toString())
                    .Book(bookResponseList)
                    .build();
            orderResponsesList.add(orderResponse);
        }
        return orderResponsesList;
    }
    @Override
    public orderResponse UpdateOrder(String StatusBook,Integer id){
        userorder userorder=userOrderRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        userorder.setStatus(StatusBook);
        userOrderRepository.save(userorder);
        List<BookResponse> bookResponseList=new ArrayList<>();
        List<orders_details> ordersDetailsList=userorder.getOrderDetails();
        for (orders_details ordersDetails:ordersDetailsList){
            BookResponse bookResponse=new BookResponse().builder()
                    .title(ordersDetails.getBook().getTitle())
                    .publisher(ordersDetails.getBook().getPublisher())
                    .image(ordersDetails.getBook().getImage().getBytes())
                    .totalpages(ordersDetails.getBook().getTotalpages())
                    .description(ordersDetails.getBook().getDescription())
                    .category(ordersDetails.getBook().getCategory())
                    .author(ordersDetails.getBook().getAuthor())
                    .build();
            bookResponseList.add(bookResponse);

        }
        orderResponse orderResponse=new orderResponse().builder()
                .id(userorder.getId())
                .status(userorder.getStatus())
                .typeTake(userorder.getTypeTake().getNametype())
                .address(userorder.getLibrarycards().getActors().getAddress())
                .fullnameUser(userorder.getLibrarycards().getActors().getFull_name())
                .phoneNumber(userorder.getLibrarycards().getActors().getPhone_number())
                .Dateend(LocalDate.now().plusDays(7).toString())
                .Book(bookResponseList)
                .build();
        return  orderResponse;
    }
    @Override
    public List<orderResponse> getAllorderByDate(String Date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkDate1=LocalDate.parse(Date,formatter);
        List<userorder> userorder1=userOrderRepository.findAll();
        List<orderResponse> orderResponsesList=new ArrayList<>();
        for (userorder userorder:userorder1){
            LocalDate checkDate=LocalDate.parse(userorder.getOrderDate(),formatter);
            if (checkDate.isEqual(checkDate1)){
                List<BookResponse> bookResponseList=new ArrayList<>();
                List<orders_details> ordersDetailsList=userorder.getOrderDetails();
                for (orders_details ordersDetails:ordersDetailsList){
                    BookResponse bookResponse=new BookResponse().builder()
                            .title(ordersDetails.getBook().getTitle())
                            .publisher(ordersDetails.getBook().getPublisher())
                            .image(ordersDetails.getBook().getImage().getBytes())
                            .totalpages(ordersDetails.getBook().getTotalpages())
                            .description(ordersDetails.getBook().getDescription())
                            .category(ordersDetails.getBook().getCategory())
                            .author(ordersDetails.getBook().getAuthor())
                            .build();
                    bookResponseList.add(bookResponse);

                }
                orderResponse orderResponse=new orderResponse().builder()
                        .id(userorder.getId())
                        .status(userorder.getStatus())
                        .typeTake(userorder.getTypeTake().getNametype())
                        .address(userorder.getLibrarycards().getActors().getAddress())
                        .fullnameUser(userorder.getLibrarycards().getActors().getFull_name())
                        .phoneNumber(userorder.getLibrarycards().getActors().getPhone_number())
                        .Dateend(LocalDate.now().plusDays(7).toString())
                        .Book(bookResponseList)
                        .build();
                orderResponsesList.add(orderResponse);
            }
        }
        return orderResponsesList;
    }
}
