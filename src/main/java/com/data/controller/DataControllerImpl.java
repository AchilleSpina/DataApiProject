package com.data.controller;

import com.data.model.UserMessage;
import com.data.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataControllerImpl implements DataController{
    @Autowired
    DataService dataService;

    @Override
    @PostMapping("/data/{customerId}/{dialogId}")
    public ResponseEntity<String> pushCustomerMessage(@PathVariable("customerId") String customerId,
                                                      @PathVariable("dialogId") String dialogId,
                                                      @RequestBody UserMessage userMessage){
        userMessage.setCustomerId(customerId);
        userMessage.setDialogId(dialogId);
        log.info("[Controller] Push customer message : " +userMessage );
        dataService.pushCustomerMessage(userMessage);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
