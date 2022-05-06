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

import java.util.HashMap;
import java.util.Map;

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
                                                      @RequestBody  Map<String,String> body){

        if(!body.containsKey("text") || body.get("text").isBlank()){
            return new ResponseEntity<>("Missing/Blank text field in Request Body", HttpStatus.BAD_REQUEST);
        }
        if(!body.containsKey("language") || body.get("language").isBlank()){
            return new ResponseEntity<>("Missing/Blank language field in Request Body", HttpStatus.BAD_REQUEST);
        }
        CustomerMessage customerMessage=CustomerMessage.builder()
                .customerId(customerId)
                .dialogId(dialogId)
                .text(body.get("text"))
                .language(body.get("language"))
                .build();
        log.info("[Controller] Push customer message : " +customerMessage );
        dataService.pushCustomerMessage(customerMessage);
        return new ResponseEntity<>("Saved:" + customerMessage, HttpStatus.OK);
    }
}
