package com.example.springrestfulpractice.controller;

import com.example.springrestfulpractice.controller.dto.request.CreateMealRequest;
import com.example.springrestfulpractice.controller.dto.request.UpdateMealRequest;
import com.example.springrestfulpractice.controller.dto.response.StatusResponse;
import com.example.springrestfulpractice.model.entity.Meal;
import com.example.springrestfulpractice.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Id;
import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping()
    public List<Meal> getAllMeals(){
        List<Meal> mealList = this.mealService.getAllMeals();
        return mealList;
    }
    @GetMapping("/{id}")
    public Meal getMealById(@PathVariable String id){
        try{
            Meal meal = this.mealService.getMealById(Integer.parseInt(id));
            return meal;
        }catch(Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found ^^");
        }
    }
    @PostMapping()
    public StatusResponse createMeal(@RequestBody CreateMealRequest request){
        try{
            StatusResponse response = this.mealService.createMeal(request);
            return response;
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found ^^");
        }
    }
    @PutMapping("/{id}")
    public StatusResponse updateMeal(@PathVariable String id, @RequestBody UpdateMealRequest request){
        try{
            StatusResponse response = this.mealService.updateMeal(Integer.parseInt(id),request);
            return response;
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found ^^");
        }
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteMeal(@PathVariable String id){
        try{
            StatusResponse response = this.mealService.deleteMeal(Integer.parseInt(id));
            return response;
        }catch(Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found ^^");
        }
    }
}
