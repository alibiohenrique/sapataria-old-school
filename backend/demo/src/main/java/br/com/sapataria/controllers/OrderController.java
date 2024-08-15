package br.com.sapataria.controllers;

import br.com.sapataria.entity.Client;
import br.com.sapataria.entity.orders.Order;
import br.com.sapataria.entity.orders.OrderStatus;
import br.com.sapataria.services.ClientService;
import br.com.sapataria.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderService orderService;
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {

        try {

            if (order.getClient() == null) {
                log.error("Client information is mission from the order");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Optional<Client> optionalClient = Optional.empty();

            if (order.getClient().getEmail() != null) {
                optionalClient = clientService.findByEmail(order.getClient().getEmail());
            }
            if (!optionalClient.isPresent() && order.getClient().getName() != null) {
                optionalClient = clientService.findByName(order.getClient().getName());
            }
            if (!optionalClient.isPresent() && order.getClient().getPhoneNumber() != null) {
                optionalClient = clientService.findByPhoneNumber(order.getClient().getPhoneNumber());
            }

            if (optionalClient.isPresent()) {
                Client existingClient = optionalClient.get();
                order.setClient(existingClient);

            } else {
                log.error("Client not found. Order not associated with any client.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            orderService.assignSequentialIdsToItem(order);

            orderService.save(order);
            Client existingClient = order.getClient();
            if (existingClient.getOrderNumbers() == null) {
                existingClient.setOrderNumbers(new ArrayList<>());
            }
            existingClient.getOrderNumbers().add(order.getOrderNumber());
            clientService.save(existingClient);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(orders);
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

    @DeleteMapping("/delete/{orderNumber}")
    public ResponseEntity<Void> delete(@PathVariable String orderNumber) {
        if (orderService.findOrderBy(orderNumber).isPresent()) {
            orderService.deleteOrder(orderNumber);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
