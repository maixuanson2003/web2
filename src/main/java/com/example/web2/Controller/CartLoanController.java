package com.example.web2.Controller;

import com.example.web2.DTO.request.CartRequest;
import com.example.web2.DTO.response.CartResponse;
import com.example.web2.Service.CartLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartLoanController {

    @Autowired
    private CartLoanService cartLoanService;

    @PutMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestBody CartRequest cartRequest, @RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        try {
            cartLoanService.addproductToCart(cartRequest,tokens);
            return ResponseEntity.ok("Product added to cart successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        try {
            cartLoanService.CreateCart(tokens);
            return ResponseEntity.ok("Cart created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelCart(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        try {
            String result = cartLoanService.CancelCart(id, tokens);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity< List<CartResponse>> getCartByLibraryCardId(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        try {
            List<CartResponse> cartResponse = cartLoanService.getCartByLibraryCardId(tokens);
            return ResponseEntity.ok(cartResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}