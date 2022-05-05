package com.data.controller;

import com.data.model.CustomerMessage;
import org.springframework.http.ResponseEntity;

public interface DataController {

     ResponseEntity<String>  pushCustomerMessage(String customerId, String dialogId, CustomerMessage customerMessage);
}
