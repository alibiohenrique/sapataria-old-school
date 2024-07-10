package br.com.sapataria.entity;

import br.com.sapataria.entity.orders.Order;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "clients")
public class Client {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private List<Order> orders;
}
