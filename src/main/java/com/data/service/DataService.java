package com.data.service;

import com.data.model.CustomerMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataService {
    void pushCustomerMessage(CustomerMessage customerMessage);
    Integer deleteCustomerMessageByDialogId(String dialogId);
    Integer updateConsetCustomerMessageByDialogId(String dialogId,Boolean consent);
    Page<CustomerMessage> getCustomerMessageBylanguageOrCustomerId(String language, String customerId, Pageable pageable);
}
