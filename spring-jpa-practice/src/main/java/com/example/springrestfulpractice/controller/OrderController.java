package com.example.springrestfulpractice.controller;

import com.example.springrestfulpractice.controller.dto.request.CreateOrderRequest;
import com.example.springrestfulpractice.controller.dto.request.UpdateOrderRequest;
import com.example.springrestfulpractice.controller.dto.response.StatusResponse;
import com.example.springrestfulpractice.model.entity.Order;
import com.example.springrestfulpractice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public List<Order> getAllOrders() {
        List<Order> orderList = this.orderService.getAllOrders();
        if (orderList.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        return orderList;
    }

    @GetMapping("/{seq}")
    public ResponseEntity<String> getOrderBySeq(@PathVariable String seq) {
        try {
            String order = this.orderService.getOrderBySeq(Integer.parseInt(seq));
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP Status
                    .contentType(MediaType.APPLICATION_JSON) // Content-Type(media type)
                    .body(order);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            String response = this.orderService.createOrder(request);
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP Status
                    .contentType(MediaType.APPLICATION_JSON) // Content-Type(media type)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id, @RequestBody UpdateOrderRequest request) {
        try {
            String response = this.orderService.updateOrder(Integer.parseInt(id), request);
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP Status
                    .contentType(MediaType.APPLICATION_JSON) // Content-Type(media type)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteOrder(@PathVariable String id) {
        try {
            StatusResponse response = this.orderService.deleteOrder(Integer.parseInt(id));
            return response;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }
}
