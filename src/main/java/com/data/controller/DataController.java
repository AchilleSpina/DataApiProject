package com.data.controller;

import com.data.model.CustomerMessage;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DataController {

     ResponseEntity<String>  pushCustomerMessage(String customerId, String dialogId, Map<String,String> body);
}
