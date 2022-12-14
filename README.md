# Spring-Restful-Practice

## OrderService

* 取得所有訂單

```java!
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
```

* 取得單筆訂單

```java!
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
```

* 新增訂單

```java!
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
```

* 更新訂單

```java!
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
```

* 刪除訂單

```java!
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
```

## Controller

* 取得所有訂單

```java!
    @GetMapping()
    public ResponseEntity<String> getAllOrders() {
        String orderList = this.orderService.getAllOrders();
        if (orderList == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderList);
    }
```

* 取得單筆訂單

```java!
    @GetMapping("/{seq}")
    public ResponseEntity<String> getOrderBySeq(@PathVariable String seq) {
        try {
            String order = this.orderService.getOrderBySeq(Integer.parseInt(seq));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(order);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }
```

* 新增訂單

```java!
    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            String response = this.orderService.createOrder(request);
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP Status
                    .contentType(MediaType.APPLICATION_JSON) // Content-Type(media type)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }
```

* 更新訂單

```java!
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id, @RequestBody UpdateOrderRequest request) {
        try {
            String response = this.orderService.updateOrder(Integer.parseInt(id), request);
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP Status
                    .contentType(MediaType.APPLICATION_JSON) // Content-Type(media type)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }
```

* 刪除訂單

```java!
    @DeleteMapping("/{id}")
    public StatusResponse deleteOrder(@PathVariable String id) {
        try {
            StatusResponse response = this.orderService.deleteOrder(Integer.parseInt(id));
            return response;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }
```
