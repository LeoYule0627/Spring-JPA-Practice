package com.example.springrestfulpractice.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMealRequest {
    private int id;
    private String name;
    private int price;
    private String description;
}
