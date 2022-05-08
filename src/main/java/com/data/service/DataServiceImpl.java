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
    public Integer deleteCustomerMessageByDialogIdAndConsent(String dialogId,Boolean consent) {
        log.info("[Service] Delete Customer message with DialogId : " + dialogId);
        Integer documenteDeleted=customerMessageRepository.deleteByDialogIdAndConsent(dialogId,consent);
        log.info("[Service] Number of document deleted: "+documenteDeleted.toString()+" with DialogId : " + dialogId);
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
    public Page<CustomerMessage> getCustomerMessageByConsentAndlanguageOrCustomerId(String language,String customerId,Boolean consent, Pageable pageable) {
        log.info("[Service] Find Customer message CustomerId:" + customerId+" Language:" + language+" Consent:"+consent);
        if (language!=null  && customerId!=null)
            return customerMessageRepository.findByCustomerIdAndLanguageAndConsentOrderByDateDesc(customerId,language,consent,pageable);
        if (language!=null )
            return customerMessageRepository.findByLanguageAndConsentOrderByDateDesc(language,consent,pageable);
        if (customerId!=null )
            return customerMessageRepository.findByCustomerIdAndConsentOrderByDateDesc(customerId,consent,pageable);
        return customerMessageRepository.findByConsentOrderByDateDesc(consent,pageable);
    }


}
