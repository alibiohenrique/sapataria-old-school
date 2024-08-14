package br.com.sapataria.entity.orders;

import br.com.sapataria.entity.Client;
import br.com.sapataria.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String orderNumber;
    private OrderStatus orderStatus;
    private boolean itsPaid;
    private BigDecimal totalPrice;
    private BigDecimal totalPaid;
    private OrderType orderType;
    private List<Item> itemsList;
    private Client client;
    private Date pickUpDate;
    private Date receivedDate;

}
