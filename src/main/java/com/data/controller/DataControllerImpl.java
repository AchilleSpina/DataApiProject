package com.data.controller;

import com.data.model.CustomerMessage;
import com.data.service.DataService;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class DataControllerImpl implements DataController{
    @Autowired
    private DataService dataService;

    @Override
    @Description("This API endpoint must be used to store the customer message.")
    @PostMapping("/data/{customerId}/{dialogId}")
    public ResponseEntity<String> pushCustomerMessage(@PathVariable("customerId") String customerId,
                                                      @PathVariable("dialogId") String dialogId,
                                                      @RequestBody  Map<String,String> body){

        if(!body.containsKey("text") || body.get("text").isBlank()){
            log.info("[Controller] Push customer message : Missing/Blank language text in Request Body");
            return new ResponseEntity<>("Missing/Blank text field in Request Body", HttpStatus.BAD_REQUEST);
        }
        if(!body.containsKey("language") || body.get("language").isBlank()){
            log.info("[Controller] Push customer message : Missing/Blank language field in Request Body");
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
        return new ResponseEntity<>("Saved: " + customerMessage, HttpStatus.OK);
    }

    @Override
    @Description("This API endpoint must be used to delete the customer messages with dialog id by consent.")
    @PostMapping("/consents/{dialogId}")
    public ResponseEntity<String> consentDialog(@PathVariable("dialogId") String dialogId,
                                                      @RequestBody  String consent){
        if(!consent.equalsIgnoreCase("true") && !consent.equalsIgnoreCase("false")){
            log.info("[Controller] Consent Dialog : Missing boolean consent in Request Body");
            return new ResponseEntity<>("Only Boolean value needed in Body for the consent! ", HttpStatus.BAD_REQUEST);
        }

        log.info("[Controller] Dialog id: "+dialogId+"  Consent: "+ consent);
        if(Boolean.parseBoolean(consent)){
            Integer documentUpdated=dataService.updateConsetCustomerMessageByDialogId(dialogId,true);
            return new ResponseEntity<>("Dialog id: "+dialogId+"  Consent: "+ consent+" Document updated: "+documentUpdated.toString(), HttpStatus.OK);
        }else{
            Integer documentDeleted=dataService.deleteCustomerMessageByDialogId(dialogId);
            return new ResponseEntity<>("Dialog id: "+dialogId+"  Consent: "+ consent+ " Document deleted: "+documentDeleted.toString(), HttpStatus.OK);
        }
    }

    @Override
    @Description("This API endpoint must be used to retrive the customer messages.")
    @GetMapping("/data/")
    public ResponseEntity<Page<CustomerMessage>> getCustomerMessage(@RequestParam("language") String language,
                                                        @RequestParam("customerId") String customerId,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "3") Integer size){
        Pageable pageable = PageRequest.of(page,size);
        log.info("[Controller] Retrive Customer Message");
        Page<CustomerMessage> customerMessagePage = dataService.getCustomerMessageBylanguageOrCustomerId(language,customerId,pageable);
        return  new ResponseEntity<>(customerMessagePage,HttpStatus.OK);
    }
}
