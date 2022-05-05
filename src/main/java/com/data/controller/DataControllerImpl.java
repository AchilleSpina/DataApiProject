package com.data.controller;

import com.data.model.CustomerMessage;
import com.data.service.DataService;
import jdk.jfr.Description;
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
    @Description("This API endpoint must be used to store the customer message.")
    @PostMapping("/data/{customerId}/{dialogId}")
    public ResponseEntity<String> pushCustomerMessage(@PathVariable("customerId") String customerId,
                                                      @PathVariable("dialogId") String dialogId,
                                                      @RequestBody CustomerMessage customerMessage){
        customerMessage.setCustomerId(customerId);
        customerMessage.setDialogId(dialogId);
        log.info("[Controller] Push customer message : " +customerMessage );
        dataService.pushCustomerMessage(customerMessage);
        return new ResponseEntity<>("Pushed customer message", HttpStatus.OK);
    }
}
