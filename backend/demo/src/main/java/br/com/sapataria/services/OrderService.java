package br.com.sapataria.services;

import br.com.sapataria.entity.Item;
import br.com.sapataria.entity.orders.Order;
import br.com.sapataria.entity.orders.OrderStatus;
import br.com.sapataria.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final CounterService counterService;
    private final OrderRepository orderRepository;


    public void save(Order order) {
        long orderNumberSequence = counterService.getNextSequence();
        String orderNumber = "A" + orderNumberSequence;
        order.setOrderNumber(orderNumber);
        log.info("Generated order number: " + orderNumber);
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderBy(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> findOrdersByOrderNumbers(List<String> orderNumbers) {
        return orderRepository.findByOrderNumberIn(orderNumbers);
    }

    public void changeOrderStatus(String orderNumber, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderNumber).orElseThrow(() -> new RuntimeException((orderNumber + " not found")));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    public void deleteOrder(String orderNumber) {
        Optional<Order> order = orderRepository.findById(orderNumber);
        if (order.isPresent()) {
            orderRepository.deleteById(orderNumber);
        } else {
            log.error("Order not found");
        }

    }

    public void assignSequentialIdsToItem(Order order) {
        long itemId = 1;
        for (Item item : order.getItemsList()) {
            item.setId(itemId++);
        }

    }
}
