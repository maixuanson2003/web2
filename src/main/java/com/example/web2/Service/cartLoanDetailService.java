package com.example.web2.Service;

import com.example.web2.DTO.request.CartRequest;
import com.example.web2.Entity.cartloan_detail;

public interface cartLoanDetailService {
    public cartloan_detail createCartDetails(CartRequest cartRequest, String token);
    public String DeleteCartDetailsById(int id);

}
