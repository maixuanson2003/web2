package com.example.web2.Service;
import com.example.web2.DTO.response.CartResponse;
import com.example.web2.Entity.cartloan;
import com.example.web2.DTO.request.CartRequest;
import com.example.web2.Entity.cartloan_detail;
import java.util.List;

public interface CartLoanService {
    public void addproductToCart(CartRequest cartRequest,String token);
    public void CreateCart(String token);
    public String CancelCart(int id,String token);
    public List<CartResponse> getCartByLibraryCardId(String token);
}
