package com.data.controller;

import com.data.model.CustomerMessage;
import com.data.service.DataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataControllerImplTest {
    private AutoCloseable closeable;
    @Mock
    DataService dataService;

    @InjectMocks
    @Spy
    DataControllerImpl dataController;

    @BeforeEach
    public void setUp(){
        closeable= MockitoAnnotations.openMocks(this);
    }

    @Test
    void pushCustomerMessage_MissingText() {
        Map<String,String> hashmap=new HashMap<>();
        hashmap.put("language","Spina");
        ResponseEntity<String> response = dataController.pushCustomerMessage("customer","dialog",hashmap);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void pushCustomerMessage_MissingLanguage() {
        Map<String,String> hashmap=new HashMap<>();
        hashmap.put("text","Achille");
        ResponseEntity<String> response = dataController.pushCustomerMessage("customer","dialog",hashmap);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void pushCustomerMessage_Right() {
        Map<String,String> hashmap=new HashMap<>();
        hashmap.put("text","Achille");
        hashmap.put("language","Spina");
        Mockito.doNothing().when(dataService).pushCustomerMessage(Mockito.any(CustomerMessage.class));
        ResponseEntity<String> response = dataController.pushCustomerMessage("customer","dialog",hashmap);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.OK);
    }
}