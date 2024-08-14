package br.com.sapataria.services;

import br.com.sapataria.entity.Counter;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@AllArgsConstructor
public class CounterService {
    private final MongoTemplate mongoTemplate;

    private static final String COUNTER_ID = "A";

    public long getNextSequence() {
        Counter counter = mongoTemplate.findAndModify(
                Query.query(Criteria.where("id").is(COUNTER_ID)),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                Counter.class
        );
        return counter.getSequence();
    }
}
