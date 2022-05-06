package com.data.repository;

import com.data.model.CustomerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMessageRepository extends MongoRepository<CustomerMessage,String> {
}
