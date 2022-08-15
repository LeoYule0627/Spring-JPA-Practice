package com.example.springrestfulpractice.model;

import com.example.springrestfulpractice.model.entity.Meal;
import com.example.springrestfulpractice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM ORDER_LIST ,MEAL WHERE ORDER_LIST.MEAL_ID=MEAL.ID"
            , nativeQuery = true)
    List<Order> findAll();
    Order findById(int id);
    List<Order> findBySeq(int seq);
    Long deleteById(int id);
    @Query(value = "SELECT SUM(MEAL.PRICE*ORDER_LIST.QUANTITY) FROM ORDER_LIST ,MEAL WHERE ORDER_LIST.MEAL_ID=MEAL.ID and ORDER_LIST.SEQ =?1 group by ORDER_LIST.SEQ"
            ,nativeQuery = true)
    int getTotalPriceBySeq(int seq);
}