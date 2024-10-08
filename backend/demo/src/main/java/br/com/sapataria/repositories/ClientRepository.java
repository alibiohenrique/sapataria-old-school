package br.com.sapataria.repositories;

import br.com.sapataria.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    Client findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    Client findByPhoneNumber(String phoneNumber);
    Client findByName(String name);
}
