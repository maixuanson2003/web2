package com.example.web2.Service;

import com.example.web2.DTO.response.orderResponse;

import java.util.List;

public interface orderService {
    public List<orderResponse> getAllOrderResponse();
    public String addOrder(Integer cartloanid,Integer bookid,Integer amount,String token,String typetake);
    public String deleteOrderById(int id);
    public  List<orderResponse> getAllOrderForUser(String token);
    public orderResponse UpdateOrder(String StatusBook,Integer id);
    public  List<orderResponse> getAllorderByDate(String Date);

}
