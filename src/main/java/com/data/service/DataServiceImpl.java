package com.data.service;

import com.data.model.CustomerMessage;
import com.data.repository.CustomerMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Autowired
    CustomerMessageRepository customerMessageRepository;

    @Override
    public void pushCustomerMessage(CustomerMessage customerMessage) {
        log.info("[Service] Push customer message : " + customerMessage);
        customerMessageRepository.insert(customerMessage);
    }
}
