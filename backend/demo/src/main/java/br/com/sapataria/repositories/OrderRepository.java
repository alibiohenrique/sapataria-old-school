package br.com.sapataria.repositories;

import br.com.sapataria.entity.orders.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order>findByOrderNumberIn(List<String> orderNumbers);
}
