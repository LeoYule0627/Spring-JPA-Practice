package com.example.springrestfulpractice.controller.dto.response;

import com.example.springrestfulpractice.model.entity.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SetResponse {
    public static String setResponseAllOrders(List<Order> order, int totalPrice) {
        JSONObject object = new JSONObject();
        if (order != null) {
            JSONArray list = new JSONArray();
            Map mealMap = new HashMap();
            Map orderMap = new HashMap();
            orderMap.put("totalPrice", totalPrice);
            for (Order orderList : order) {
                mealMap.put("quantity", orderList.getQuantity());
                mealMap.put("mealName", orderList.getMealName());
                mealMap.put("mealPrice",orderList.getMealPrice());
                orderMap.put("waiter", orderList.getWaiter());
                orderMap.put("seq", orderList.getSeq());
                list.put(mealMap);
            }
            object.put("Order",orderMap);
            object.put("mealList", list);
        }
        return object.toString();
    }
    public static String setResponseBySeq(String returnCode, String returnMsg, List<Order> order, int totalPrice) {
        JSONObject object = new JSONObject();
        object.put("returnCode", returnCode);
        object.put("returnMsg", returnMsg);
        if (order != null) {
            JSONArray list = new JSONArray();
            Map mealMap = new HashMap();
            Map orderMap = new HashMap();
            orderMap.put("totalPrice", totalPrice);
            for (Order orderList : order) {
                mealMap.put("quantity", orderList.getQuantity());
                mealMap.put("mealName", orderList.getMealName());
                mealMap.put("mealPrice",orderList.getMealPrice());
                orderMap.put("waiter", orderList.getWaiter());
                orderMap.put("seq", orderList.getSeq());
                list.put(mealMap);
            }
            object.put("Order",orderMap);
            object.put("mealList", list);
        }
        return object.toString();
    }

    public static String setResponseOrder(String returnCode, String returnMsg, Order order) {
        JSONObject object = new JSONObject();
        object.put("returnCode", returnCode);
        object.put("returnMsg", returnMsg);
        if (order != null) {
            JSONArray list = new JSONArray();
            Map mealMap = new HashMap();
            Map orderMap = new HashMap();
            mealMap.put("quantity", order.getQuantity());
            mealMap.put("mealName", order.getMealName());
            mealMap.put("mealPrice",order.getMealPrice());
            orderMap.put("waiter", order.getWaiter());
            orderMap.put("seq", order.getSeq());
            list.put(mealMap);
            object.put("Order",orderMap);
            object.put("mealList", list);
        }
        return object.toString();
    }
}
