package br.com.sapataria.controllers;

import br.com.sapataria.entity.orders.Order;
import br.com.sapataria.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
