package com.data.service;

import com.data.model.CustomerMessage;
import com.data.repository.CustomerMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Autowired
    private CustomerMessageRepository customerMessageRepository;

    @Override
    public void pushCustomerMessage(CustomerMessage customerMessage) {
        log.info("[Service] Push customer message : " + customerMessage);
        customerMessageRepository.insert(customerMessage);
    }

    @Override
    public Integer deleteCustomerMessageByDialogId(String dialogId) {
        log.info("[Service] Delete Customer message with DialogId : " + dialogId);
        Integer documenteDeleted=0;
        if (customerMessageRepository.existsByDialogIdAndConsent(dialogId,false)){
            documenteDeleted=customerMessageRepository.deleteByDialogId(dialogId);
            log.info("[Service] Number of document deleted: "+documenteDeleted.toString()+" with DialogId : " + dialogId);
        }
        return documenteDeleted;
    }

    @Override
    public Integer updateConsetCustomerMessageByDialogId(String dialogId,Boolean consent) {
        log.info("[Service] Update Customer message with DialogId : " + dialogId+" with consent:" + consent.toString());
        Integer documenteUpdated=customerMessageRepository.updateConsentByDialogId(dialogId,consent);
        log.info("[Service] Number of document updated: "+documenteUpdated.toString()+" with DialogId : " + dialogId);
        return documenteUpdated;
    }

    @Override
    public Page<CustomerMessage> getCustomerMessageBylanguageOrCustomerId(String language, String customerId, Pageable pageable) {
        log.info("[Service] Find Customer message with customerId : " + customerId+" and language:" + language);
        if (language!=null  && customerId!=null)
            return customerMessageRepository.findByCustomerIdAndLanguageAndConsentOrderByDateDesc(customerId,language,true,pageable);
        if (language!=null )
            return customerMessageRepository.findByLanguageAndConsentOrderByDateDesc(language,true,pageable);
        if (customerId!=null )
            return customerMessageRepository.findByCustomerIdAndConsentOrderByDateDesc(customerId,true,pageable);
        return customerMessageRepository.findByConsentOrderByDateDesc(true,pageable);
    }


}
