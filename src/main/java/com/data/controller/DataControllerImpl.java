package com.data.controller;

import com.data.model.CustomerMessage;
import com.data.model.Message;
import com.data.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.ContentType;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name=" Data controller",description="Used to save, manage and retrive the customer data.")
@Slf4j
public class DataControllerImpl implements DataController{
    @Autowired
    private DataService dataService;

    @Override
    @Operation(description = "This endpoint allows to store the customer messages.",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success", content=@Content(mediaType = "String")),
                    @ApiResponse(responseCode = "400",description = "Invalid input", content=@Content(mediaType = "String")),
                    @ApiResponse(responseCode = "500",description = "Internal server error", content=@Content),

            }
    )
    @PostMapping("/data/{customerId}/{dialogId}")
    public ResponseEntity<String> pushCustomerMessage(
            @Parameter(description = "The Id of the customer") @PathVariable("customerId") String customerId,
            @Parameter(description = "The Id of the dialog") @PathVariable("dialogId") String dialogId,
            @Parameter(description = "The text and language of the message.", schema = @Schema(implementation = Message.class)) @RequestBody  Map<String,String> body){

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
    @Operation(description = "This endpoint must be used to update the consent of customer messages by dialog id. If false it should delete the customer's data.",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success", content=@Content(mediaType = "String")),
                    @ApiResponse(responseCode = "400",description = "Invalid input", content=@Content(mediaType = "String")),
                    @ApiResponse(responseCode = "500",description = "Internal server error", content=@Content),

            }
    )
    @PostMapping("/consents/{dialogId}")
    public ResponseEntity<String> consentDialog(
            @Parameter(description = "The Id of the dialog")  @PathVariable("dialogId") String dialogId,
            @Parameter(description = "The consent for the dialog.",schema=@Schema(implementation = Boolean.class)) @RequestBody  String consent){
        if(!consent.equalsIgnoreCase("true") && !consent.equalsIgnoreCase("false")){
            log.info("[Controller] Consent Dialog : Missing boolean consent in Request Body");
            return new ResponseEntity<>("Only Boolean value needed in Body for the consent! ", HttpStatus.BAD_REQUEST);
        }

        log.info("[Controller] Dialog id: "+dialogId+"  Consent: "+ consent);
        if(Boolean.parseBoolean(consent)){
            Integer documentUpdated=dataService.updateConsetCustomerMessageByDialogId(dialogId,true);
            return new ResponseEntity<>("Dialog id: "+dialogId+"  Consent: "+ consent+" Document updated: "+documentUpdated.toString(), HttpStatus.OK);
        }else{
            Integer documentDeleted=dataService.deleteCustomerMessageByDialogIdAndConsent(dialogId,false);
            return new ResponseEntity<>("Dialog id: "+dialogId+"  Consent: "+ consent+ " Document deleted: "+documentDeleted.toString(), HttpStatus.OK);
        }
    }

    @Override
    @Operation(description = "This API endpoint must be used to retrive the customer messages (with consent true) by customer id and language (if any).",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Success"),
                    @ApiResponse(responseCode = "400",description = "Invalid input", content=@Content),
                    @ApiResponse(responseCode = "500",description = "Internal server error", content=@Content),

            }
    )
    @GetMapping("/data/")
    public ResponseEntity<Page<CustomerMessage>> getCustomerMessage(
            @Parameter(description = "The language of the messages") @RequestParam(value="language", required=false) String language,
            @Parameter(description = "The Id of the customer") @RequestParam(value="customerId", required=false) String customerId,
            @Parameter(description = "Index of page to display") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Number of results per page") @RequestParam(defaultValue = "3") Integer size){
        if(language!=null && language.isBlank()){
            log.info("[Controller] Get Customer Message : RequestParam language is blank.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(customerId!=null && customerId.isBlank()){
            log.info("[Controller] Get Customer Message : RequestParam customerId is blank.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page,size, Sort.by("date").descending());
        log.info("[Controller] Retrive Customer Message");
        Page<CustomerMessage> customerMessagePage = dataService.getCustomerMessageByConsentAndlanguageOrCustomerId(language,customerId,true,pageable);
        return  new ResponseEntity<>(customerMessagePage,HttpStatus.OK);
    }
}
