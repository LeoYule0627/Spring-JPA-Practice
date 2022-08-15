package com.example.springrestfulpractice.model;

import com.example.springrestfulpractice.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    @Query(value = "SELECT COUNT(MEAL.ID) FROM MEAL"
    ,nativeQuery = true)
    int countMeal();
    Meal findById(int id);
    Long deleteById(int id);
    @Query(value = "SELECT MEAL.NAME FROM MEAL WHERE MEAL.ID=?1"
    ,nativeQuery = true)
    String getMealNameById(int id);
    @Query(value = "SELECT MEAL.PRICE FROM MEAL WHERE MEAL.ID=?1"
            ,nativeQuery = true)
    int getMealPriceById(int id);
}
