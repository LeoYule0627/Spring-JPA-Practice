package com.example.springrestfulpractice.service;

import com.example.springrestfulpractice.controller.dto.request.CreateMealRequest;
import com.example.springrestfulpractice.controller.dto.request.UpdateMealRequest;
import com.example.springrestfulpractice.controller.dto.response.StatusResponse;
import com.example.springrestfulpractice.model.MealRepository;
import com.example.springrestfulpractice.model.OrderRepository;
import com.example.springrestfulpractice.model.entity.Meal;
import com.example.springrestfulpractice.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<Meal> getAllMeals(){
        List<Meal> response = this.mealRepository.findAll();
        return response;
    }
    public Meal getMealById(int id){
        Meal response = this.mealRepository.findById(id);
        return response;
    }
    public StatusResponse createMeal(CreateMealRequest request){
        try{
            Meal createMeal = new Meal();
            createMeal = createAndUpdate(createMeal,request);
            this.mealRepository.save(createMeal);
            return new StatusResponse("0000", "successfully");
        }catch (Exception e){
            System.out.println(e);
            return new StatusResponse("0001", "FAIL");
        }
    }
    public Meal createAndUpdate(Meal meal,CreateMealRequest request){
        meal.setId(request.getId());
        meal.setName(request.getName());
        meal.setPrice(request.getPrice());
        meal.setDescription(request.getDescription());
        return meal;
    }

    public StatusResponse updateMeal(int id,UpdateMealRequest request){
        try{
            Meal updateMeal = this.mealRepository.findById(id);
            updateMeal = createAndUpdate(updateMeal,request);
            this.mealRepository.save(updateMeal);
            return new StatusResponse("0000", "successfully");
        }catch (Exception e){
            System.out.println(e);
            return new StatusResponse("0001", "FAIL");
        }
    }
    public Meal createAndUpdate(Meal meal, UpdateMealRequest request){
        meal.setId(request.getId());
        meal.setName(request.getName());
        meal.setPrice(request.getPrice());
        meal.setDescription(request.getDescription());
        return meal;
    }
    public StatusResponse deleteMeal(int id){
        Meal deleteMeal = this.mealRepository.findById(id);
        if(null == deleteMeal){
            return new StatusResponse("0002", "Not Found^^");
        }
        List<Order> orderList = this.orderRepository.findAll();
        for(Order order:orderList){
            if(order.getMealId()==id)
            this.orderRepository.deleteById(order.getId());
        }
        Long count = this.mealRepository.deleteById(id);
        return new StatusResponse("0000", "successfully");
    }
}
