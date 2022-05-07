package com.data.repository;

import com.data.model.CustomerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMessageRepository extends MongoRepository<CustomerMessage,String> {
    Integer deleteByDialogId(String dialogId);
}
