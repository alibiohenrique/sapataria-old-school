package br.com.sapataria.services;

import br.com.sapataria.entity.orders.Order;
import br.com.sapataria.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
