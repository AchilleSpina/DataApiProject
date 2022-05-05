package com.data.service;

import com.data.model.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Override
    public void pushCustomerMessage(UserMessage userMessage) {
        log.info("[Service] Push customer message : " +userMessage );
        //TODO Db conf and db config
    }
}
