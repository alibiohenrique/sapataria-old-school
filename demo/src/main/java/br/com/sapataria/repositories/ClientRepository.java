package br.com.sapataria.repositories;

import br.com.sapataria.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    public Client findByEmail(String email);
    public boolean existsByEmail(String email);
    public void deleteByEmail(String email);
}
