package br.com.sapataria.services;

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
    private final OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderBy(String orderNumber) {
        return orderRepository.findById(orderNumber);
    }

    public void changeOrderStatus(String orderNumber, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderNumber).get();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    public void deleteOrder(String orderNumber) {
        if(orderRepository.existsById(orderNumber)) {
            orderRepository.deleteById(orderNumber);
        } else {
            log.error("Order not found");
        }

    }

}
