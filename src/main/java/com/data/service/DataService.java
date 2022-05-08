package com.data.service;

import com.data.model.CustomerMessage;

public interface DataService {
    void pushCustomerMessage(CustomerMessage customerMessage);
    Integer deleteCustomerMessageByDialogId(String dialogId);
    Integer updateConsetCustomerMessageByDialogId(String dialogId,Boolean consent);
}
