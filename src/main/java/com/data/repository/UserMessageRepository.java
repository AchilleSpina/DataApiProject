package com.data.repository;

import com.data.model.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessageRepository extends MongoRepository<UserMessage,String> {
}
