package com.data.controller;

import com.data.model.CustomerMessage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface DataController {

     ResponseEntity<String>  pushCustomerMessage(String customerId, String dialogId, Map<String,String> body);
     ResponseEntity<String> consentDialog(String dialogId,String consent);
     ResponseEntity<Page<CustomerMessage>> getCustomerMessage(String language, String customerId, Integer page, Integer size);
}
