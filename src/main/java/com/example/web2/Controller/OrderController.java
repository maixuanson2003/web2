package com.example.web2.Controller;

import com.example.web2.DTO.response.orderResponse;
import com.example.web2.Service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private orderService orderService;

    // Endpoint to create a new order
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<String> addOrder( @RequestParam(required = false) Integer cartloanid,
                                           @RequestParam(required = false) Integer bookid,
                                            @RequestParam(required = false)Integer amount,
                                            @RequestHeader("Authorization")String token,
                                            @RequestParam(required = false) String typetakes) {
        String tokens = token.replace("Bearer ", "");
        String result = orderService.addOrder(cartloanid, bookid, amount,tokens, typetakes);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order creation failed");
        }
    }

    // Endpoint to delete an order by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable int id) {
        String result = orderService.deleteOrderById(id);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get all orders
    @GetMapping("/all")
    public ResponseEntity<List<orderResponse>> getAllOrders() {
        List<orderResponse> orders = orderService.getAllOrderResponse();
        return ResponseEntity.ok(orders);
    }

    // Endpoint to get all orders for a specific user
    @GetMapping("/user")
    public ResponseEntity<List<orderResponse>> getAllOrdersForUser(@RequestHeader("Authorization") String token) {
        String tokens = token.replace("Bearer ", "");
        List<orderResponse> orders = orderService.getAllOrderForUser(tokens);
        return ResponseEntity.ok(orders);
    }

    // Endpoint to update the status of an order
    @PutMapping("/update/{id}")
    public ResponseEntity<orderResponse> updateOrder(@RequestParam String statusBook, @PathVariable Integer id) {
        orderResponse updatedOrder = orderService.UpdateOrder(statusBook, id);
        return ResponseEntity.ok(updatedOrder);
    }

    // Endpoint to get all orders by date
    @GetMapping("/date")
    public ResponseEntity<List<orderResponse>> getAllOrdersByDate(@RequestParam String date) {
        List<orderResponse> orders = orderService.getAllorderByDate(date);
        return ResponseEntity.ok(orders);
    }
}

