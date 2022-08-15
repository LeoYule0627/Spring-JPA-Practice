package com.example.springrestfulpractice.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderRequest {
    private int id;
    private int seq;
    private String waiter;
    private int mealId;
    private int quantity;
    private String mealName;
}
