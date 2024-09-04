package com.example.web2.Service;

import com.example.web2.Entity.orders_details;

public interface orderDetailService {
    public void createOrderDetailsByCart(Integer cartloanid,Integer orderID);

    public  void createOrderDetailsByRequest(Integer bookId, Integer amount,Integer orderID);
}
