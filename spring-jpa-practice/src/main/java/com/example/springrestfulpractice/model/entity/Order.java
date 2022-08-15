package com.example.springrestfulpractice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

// 使用 Lombok 加入 Getter, Setter, Constructor
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "seq", table = "order_list")
    private int seq;
    @Column(name = "waiter", table = "order_list")
    private String waiter;
    @Column(name = "meal_id", table = "order_list")
    private int mealId;
    @Column(name = "meal_name", table = "order_list")
    private String mealName;
    @Column(name = "meal_price", table = "order_list")
    private int mealPrice;
    @Column(name = "quantity", table = "order_list")
    private int quantity;
}