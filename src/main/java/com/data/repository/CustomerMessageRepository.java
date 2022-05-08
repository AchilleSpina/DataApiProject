package com.data.repository;

import com.data.model.CustomerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMessageRepository extends MongoRepository<CustomerMessage,String>,CustomerMessageRepositoryCustom {
    Integer deleteByDialogId(String dialogId);
    List<CustomerMessage> findByDialogId(String dialogId);
    Boolean existsByDialogIdAndConsent(String dialogId,Boolean consent);
}
