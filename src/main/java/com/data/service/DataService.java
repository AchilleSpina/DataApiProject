package com.data.service;

import com.data.model.CustomerMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataService {
    void pushCustomerMessage(CustomerMessage customerMessage);
    Integer deleteCustomerMessageByDialogIdAndConsent(String dialogId,Boolean consent);
    Integer updateConsetCustomerMessageByDialogId(String dialogId,Boolean consent);
    Page<CustomerMessage> getCustomerMessageByConsentAndlanguageOrCustomerId(String language,String customerId,Boolean content, Pageable pageable);
}
