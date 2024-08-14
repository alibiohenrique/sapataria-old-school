package br.com.sapataria.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "clients")
public class Client {

    @Id
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private List<String> orderNumbers = new ArrayList<>();

}
