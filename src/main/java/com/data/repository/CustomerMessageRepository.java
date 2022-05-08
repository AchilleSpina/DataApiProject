package com.data.repository;

import com.data.model.CustomerMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMessageRepository extends MongoRepository<CustomerMessage,String>,CustomerMessageRepositoryCustom {
    Integer deleteByDialogIdAndConsent(String dialogId,Boolean consent);
    List<CustomerMessage> findByDialogId(String dialogId);
    Page<CustomerMessage> findByConsentOrderByDateDesc(Boolean consent, Pageable pageable);
    Page<CustomerMessage> findByCustomerIdAndConsentOrderByDateDesc(String customerId, Boolean consent, Pageable pageable);
    Page<CustomerMessage> findByLanguageAndConsentOrderByDateDesc(String language, Boolean consent, Pageable pageable);
    Page<CustomerMessage> findByCustomerIdAndLanguageAndConsentOrderByDateDesc(String customerId,String language, Boolean consent, Pageable pageable);
}
