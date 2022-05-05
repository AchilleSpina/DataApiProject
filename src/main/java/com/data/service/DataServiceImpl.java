package com.data.service;

import com.data.model.UserMessage;
import com.data.repository.UserMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Autowired
    UserMessageRepository userMessageRepository;
    @Override
    public void pushCustomerMessage(UserMessage userMessage) {
        log.info("[Service] Push customer message : " + userMessage);
        userMessageRepository.insert(userMessage);
    }
}
