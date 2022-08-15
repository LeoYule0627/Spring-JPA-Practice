package com.example.springrestfulpractice.service;

import com.example.springrestfulpractice.controller.dto.request.CreateOrderRequest;
import com.example.springrestfulpractice.controller.dto.request.UpdateOrderRequest;
import com.example.springrestfulpractice.controller.dto.response.SetResponse;
import com.example.springrestfulpractice.controller.dto.response.StatusResponse;
import com.example.springrestfulpractice.model.MealRepository;
import com.example.springrestfulpractice.model.OrderRepository;
import com.example.springrestfulpractice.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springrestfulpractice.controller.dto.response.SetResponse.setResponseOrder;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MealRepository mealRepository;

    public List<Order> getAllOrders() {
        List<Order> orderList = this.orderRepository.findAll();
        //將 order 的 mealName、mealPrice 對照 meal 的資訊做更新
        //目的在於 meal 的 name、price 做更新後，order 照樣會更新
        for (Order order : orderList) {
            order.setMealName(this.mealRepository.getMealNameById(order.getMealId()));
            order.setMealPrice(this.mealRepository.getMealPriceById(order.getMealId()));
        }
        return orderList;
    }

    public String getOrderBySeq(int seq) {
        List<Order> orderList = this.orderRepository.findBySeq(seq);
        if (0 == orderList.size()) {
            return SetResponse.setResponseBySeq("0003", "Not Found^^", null, 0);
        }
        //get totalPrice 找到同筆訂單的 meal.price * order.quantity 金額相加
        // 因為 price 是 meal 的金額，所以 meal 的 price 做更動後，totalPrice 也會同步更動
        int totalPrice = this.orderRepository.getTotalPriceBySeq(seq);
        //將 order 的 mealName、mealPrice 對照 meal 的資訊做更新
        for (Order order : orderList) {
            order.setMealName(this.mealRepository.getMealNameById(order.getMealId()));
            order.setMealPrice(this.mealRepository.getMealPriceById(order.getMealId()));
        }
        return SetResponse.setResponseBySeq("0000", "successfully", orderList, totalPrice);
    }

    public String createOrder(CreateOrderRequest request) {
        try {
            Order createOrder = new Order();
            // order 的 mealId 不能小於 meal 的餐數
            // 不能新增 meal 上沒有的餐點的 order
            // 例如: order 要 5 號餐，但 meal 沒有 5 號餐，就不能create order
            if (request.getMealId() <= this.mealRepository.countMeal()) {
                createOrder = createAndUpdate(createOrder, request);
                this.orderRepository.save(createOrder);
                return setResponseOrder("0000", "successfully", createOrder);
            }
            return setResponseOrder("0002", "MealId Not Found", null);
        } catch (Exception e) {
            System.out.println(e);
            return setResponseOrder("0001", "FAIL", null);
        }
    }

    public Order createAndUpdate(Order order, CreateOrderRequest request) {
        order.setId(request.getId());
        order.setSeq(request.getSeq());
        order.setMealId(request.getMealId());
        order.setQuantity(request.getQuantity());
        order.setWaiter(request.getWaiter());
        order.setMealName(this.mealRepository.getMealNameById(request.getMealId()));
        order.setMealPrice(this.mealRepository.getMealPriceById(request.getMealId()));
        return order;
    }

    public String updateOrder(int id, UpdateOrderRequest request) {
        try {
            Order updateOrder = this.orderRepository.findById(id);
            // order 的 mealId 不能小於 meal 的餐數
            // 不能更新 meal 上沒有的餐點的 order
            // 例如: order 要更改成 5 號餐，但 meal 沒有 5 號餐，就不能update order
            if (request.getMealId() <= this.mealRepository.countMeal()) {
                updateOrder = createAndUpdate(updateOrder, request);
                this.orderRepository.save(updateOrder);
                return setResponseOrder("0000", "successfully", updateOrder);
            } else return setResponseOrder("0002", "MealId Not Found", null);
        } catch (Exception e) {
            System.out.println(e);
            return setResponseOrder("0001", "FAIL", null);
        }
    }

    public Order createAndUpdate(Order order, UpdateOrderRequest request) {
        order.setId(request.getId());
        order.setSeq(request.getSeq());
        order.setMealId(request.getMealId());
        order.setQuantity(request.getQuantity());
        order.setWaiter(request.getWaiter());
        order.setMealName(mealRepository.getMealNameById(request.getMealId()));
        order.setMealPrice(this.mealRepository.getMealPriceById(request.getMealId()));
        return order;
    }

    public StatusResponse deleteOrder(int id) {
        Order deleteOrder = this.orderRepository.findById(id);
        if (null == deleteOrder) {
            return new StatusResponse("0003", "Not Found^^");
        }
        Long count = this.orderRepository.deleteById(id);
        return new StatusResponse("0000", "successfully");
    }
}
