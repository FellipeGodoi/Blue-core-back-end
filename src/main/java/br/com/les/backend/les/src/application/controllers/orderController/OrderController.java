package br.com.les.backend.les.src.application.controllers.orderController;

import br.com.les.backend.les.src.application.dto.orderRequest.CreateOrderRequest;
import br.com.les.backend.les.src.application.dto.orderRequest.OrderResponse;
import br.com.les.backend.les.src.model.orderModels.Order;
import br.com.les.backend.les.src.service.orderService.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping ("/listall")
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<OrderResponse> orders = orderService.listAllOrders();
        return ResponseEntity.ok(orders);
    }
}
