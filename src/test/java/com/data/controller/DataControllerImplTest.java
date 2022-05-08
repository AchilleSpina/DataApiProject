package com.data.controller;

import com.data.model.CustomerMessage;
import com.data.service.DataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataControllerImplTest {
    private AutoCloseable closeable;
    @Mock
    private DataService dataService;

    @InjectMocks
    @Spy
    private DataControllerImpl dataController;

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

    @Test
    void consentDialog_MissingConsent() {
        ResponseEntity<String> response = dataController.consentDialog("dialogid","");
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void consentDialog_NotBooleanConsent() {
        ResponseEntity<String> response = dataController.consentDialog("dialogid","AchilleSpina");
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void consentDialog_TrueConsent_Right() {
        Mockito.when(dataService.deleteCustomerMessageByDialogIdAndConsent(Mockito.any(String.class),Mockito.any(Boolean.class))).thenReturn(1);
        ResponseEntity<String> response = dataController.consentDialog("dialogid","true");
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.OK);
    }
    @Test
    void consentDialog_FalseConsent_Right() {
        Mockito.when(dataService.deleteCustomerMessageByDialogIdAndConsent(Mockito.any(String.class),Mockito.any(Boolean.class))).thenReturn(1);
        ResponseEntity<String> response = dataController.consentDialog("dialogid","false");
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.OK);
    }
    @Test
    void getCustomerMessage_BlankCustomer() {
        ResponseEntity<Page<CustomerMessage>> response = dataController.getCustomerMessage("EN","",1,1);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void getCustomerMessage_BlankLanguage() {
        ResponseEntity<Page<CustomerMessage>> response = dataController.getCustomerMessage("","Achille",1,1);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.BAD_REQUEST);
    }
    @Test
    void getCustomerMessage_Right() {
        List<CustomerMessage> customerMessage = new ArrayList<>();
        Mockito.when(dataService.getCustomerMessageByConsentAndlanguageOrCustomerId(Mockito.any(String.class),Mockito.any(String.class),Mockito.any(Boolean.class),Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(customerMessage));
        ResponseEntity<Page<CustomerMessage>> response = dataController.getCustomerMessage("EN","Achille",0,1);
        Assertions.assertTrue(response.getStatusCode()== HttpStatus.OK);
    }
}