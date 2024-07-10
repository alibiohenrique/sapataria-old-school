package br.com.sapataria.entity.orders;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private int orderNumber;
    private OrderStatus orderStatus;
    private boolean itsPaid;
    private BigDecimal totalPrice;
    private OrderType orderType;

}
