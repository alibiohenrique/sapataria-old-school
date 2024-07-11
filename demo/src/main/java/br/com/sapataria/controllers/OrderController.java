package br.com.sapataria.controllers;

import br.com.sapataria.entity.orders.Order;
import br.com.sapataria.entity.orders.OrderStatus;
import br.com.sapataria.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        try {
            orderService.save(order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        if (orderService.getAllOrders().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(orderService.getAllOrders());
        }
    }

    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<Order> getOrderBy(@PathVariable String orderNumber) {
        Optional<Order> order = orderService.findOrderBy(orderNumber);
        return order.isPresent() ? ResponseEntity.ok(order.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/change-status/{orderNumber}")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable String orderNumber, @RequestBody OrderStatus orderStatus) {
        if (orderService.findOrderBy(orderNumber).isPresent()) {
            orderService.changeOrderStatus(orderNumber, orderStatus);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
