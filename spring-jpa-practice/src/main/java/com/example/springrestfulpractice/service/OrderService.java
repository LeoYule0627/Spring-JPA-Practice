package com.example.springrestfulpractice.service;

import com.example.springrestfulpractice.controller.dto.request.CreateOrderRequest;
import com.example.springrestfulpractice.controller.dto.request.UpdateOrderRequest;
import com.example.springrestfulpractice.controller.dto.response.SetResponse;
import com.example.springrestfulpractice.controller.dto.response.StatusResponse;
import com.example.springrestfulpractice.model.MealRepository;
import com.example.springrestfulpractice.model.OrderRepository;
import com.example.springrestfulpractice.model.entity.Meal;
import com.example.springrestfulpractice.model.entity.Order;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.springrestfulpractice.controller.dto.response.SetResponse.setResponseOrder;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MealRepository mealRepository;

    public String getAllOrders() {
        List orderListAll = new ArrayList();
        // 先取得有幾筆訂單
        for (int i = 1; i <= this.orderRepository.maxSeq(); i++) {
            // 取得訂單資訊，若沒有就跳過
            List<Order> orderList = this.orderRepository.findBySeq(i);
            if(0==orderList.size())continue;
            //get totalPrice 找到同筆訂單的 meal.price * order.quantity 金額相加
            // 因為 price 是 meal 的金額，所以 meal 的 price 做更動後，totalPrice 也會同步更動
            int totalPrice = this.orderRepository.getTotalPriceBySeq(i);
            //將 order 的 mealName、mealPrice 對照 meal 的資訊做更新
            for (Order order : orderList) {
                order.setMealName(this.mealRepository.getMealNameById(order.getMealId()));
                order.setMealPrice(this.mealRepository.getMealPriceById(order.getMealId()));
                this.orderRepository.save(order);
            }
            // 將訂單資料加進 list，一併 return
            orderListAll.add(SetResponse.setResponseAllOrders(orderList, totalPrice));
        }
        return orderListAll.toString();
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
            this.orderRepository.save(order);
        }
        return SetResponse.setResponseBySeq("0000", "successfully", orderList, totalPrice);
    }

    public String createOrder(CreateOrderRequest request) {
        try {
            Order createOrder = new Order();
            // 不能新增 meal 上沒有的餐點的 order
            // 例如: order 要 5 號餐，但 meal 沒有 5 號餐，就不能create order
            Meal check = this.mealRepository.findById(request.getMealId());
            if(check != null){
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
            // 不能更新 meal 上沒有的餐點的 order
            // 例如: order 要更改成 5 號餐，但 meal 沒有 5 號餐，就不能update order
            Meal check = this.mealRepository.findById(request.getMealId());
            if(check != null){
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

    public StatusResponse deleteOrder(int seq) {
        List<Order> deleteOrder = this.orderRepository.findBySeq(seq);
        if (0 == deleteOrder.size()) {
            return new StatusResponse("0003", "Not Found^^");
        }
        for(Order order : deleteOrder){
            this.orderRepository.deleteById(order.getId());
        }
        return new StatusResponse("0000", "successfully");
    }
}
