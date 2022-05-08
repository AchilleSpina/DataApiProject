package com.data.repository;

import com.data.model.CustomerMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataMongoTest
class CustomerMessageRepositoryTest {
    @Autowired
    private CustomerMessageRepository customerMessageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private CustomerMessage customerMessage1;
    private CustomerMessage customerMessage2;
    private CustomerMessage customerMessage3;

    @BeforeEach
    public void setUp(){
        customerMessage1=CustomerMessage.builder()
                .customerId("Francesco")
                .dialogId("dialogid1")
                .text("blabla")
                .language("En").build();
        customerMessage2=CustomerMessage.builder()
                .customerId("Marco")
                .dialogId("dialogid2")
                .text("blabla")
                .language("En").build();
        customerMessage3=CustomerMessage.builder()
                .customerId("Gina")
                .dialogId("dialog3")
                .text("blabla")
                .language("En").build();
    }

    @AfterEach
    public void cleanUpDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    void deleteCustomerMessageByDialogId_ExistingDocumentWithDialogId(){
        String dialogidTest="Achille";
        customerMessage1.setDialogId(dialogidTest);
        customerMessage2.setDialogId(dialogidTest);
        mongoTemplate.insert(customerMessage1);
        mongoTemplate.insert(customerMessage2);
        mongoTemplate.insert(customerMessage3);
        Integer documentDeleted=customerMessageRepository.deleteByDialogId(dialogidTest);
        Assertions.assertEquals(documentDeleted,2);

    }

    @Test
    void deleteCustomerMessageByDialogId_NotExistingDocumentWithDialogId(){
        String dialogidTest="Achille";
        customerMessage1.setDialogId(dialogidTest);
        mongoTemplate.insert(customerMessage1);
        Integer documentDeleted=customerMessageRepository.deleteByDialogId("DialogIdNotPresent");
        Assertions.assertEquals(documentDeleted,0);
    }

    @Test
    void updateCustomerMessageByDialogId_ExistingDocumentWithDialogId(){
        //Insert a document
        String dialogidTest="Achille";
        customerMessage1.setDialogId(dialogidTest);
        customerMessage1.setConsent(false);
        mongoTemplate.insert(customerMessage1);
        mongoTemplate.insert(customerMessage2);
        //Update the document
        Boolean consent=true;
        Integer documentUpdated=customerMessageRepository.updateConsentByDialogId(dialogidTest,consent);
        //Check
        List<CustomerMessage> customerMessageList = customerMessageRepository.findByDialogId(dialogidTest);
        Assertions.assertEquals(customerMessageList.get(0).getConsent(),consent);
        Assertions.assertEquals(documentUpdated,1);
    }

    @Test
    void updateCustomerMessageByDialogId_NotExistingDocumentWithDialogId(){
        //Insert a document
        String dialogidTest="Achille";
        customerMessage1.setDialogId(dialogidTest);
        customerMessage1.setConsent(false);
        mongoTemplate.insert(customerMessage1);
        //Update a no existring document
        Integer documentUpdated=customerMessageRepository.updateConsentByDialogId("DialogIdNotPresent",true);
        //Check
        Assertions.assertEquals(documentUpdated,0);

    }

    @Test
    void existDocument_withDialogIdAndConsent(){
        //Insert a document
        String dialogidTest="Achille";
        customerMessage1.setDialogId(dialogidTest);
        customerMessage1.setConsent(false);
        mongoTemplate.insert(customerMessage1);
        Boolean condition=customerMessageRepository.existsByDialogIdAndConsent(dialogidTest,false);
        Assertions.assertTrue(condition);
        condition=customerMessageRepository.existsByDialogIdAndConsent(dialogidTest,true);
        Assertions.assertFalse(condition);
    }

    @Test
    void findByCustomerIdAndConsentOrderByDateDesc_checkOrdering(){
        //Setup for the test
        String customerid="Achille";
        //Oldest document
        customerMessage1.setCustomerId(customerid);
        customerMessage1.setText("I am the oldest");
        customerMessage1.setConsent(true);
        mongoTemplate.insert(customerMessage1);
        //Newest document
        customerMessage2.setCustomerId(customerid);
        customerMessage2.setConsent(true);
        customerMessage2.setText("I am the newest");
        mongoTemplate.insert(customerMessage2);


        Pageable pageable = PageRequest.of(0,1);
        Page<CustomerMessage> page0=customerMessageRepository.findByCustomerIdAndConsentOrderByDateDesc(customerid,true,pageable);
        Date messageFirstPage=page0.get().findFirst().get().getDate();
        pageable = PageRequest.of(1,1);
        Page<CustomerMessage> page1=customerMessageRepository.findByCustomerIdAndConsentOrderByDateDesc(customerid,true,pageable);
        Date messageSecondPage=page1.get().findFirst().get().getDate();
        //Assert element in page 0 is newest compare to element in page 1
        Assertions.assertTrue(messageFirstPage.after(messageSecondPage));
    }
    @Test
    void findByCustomerIdAndConsentOrderByDateDesc_checkMatching(){
        //Setup for the test
        String customerid="Achille";
        //Matching document
        customerMessage1.setCustomerId(customerid);
        customerMessage1.setConsent(true);
        mongoTemplate.insert(customerMessage1);
        //Not matching document
        customerMessage2.setCustomerId(customerid);
        customerMessage2.setConsent(false);
        mongoTemplate.insert(customerMessage2);
        //Not matching document
        customerMessage3.setCustomerId("notmatching");
        customerMessage3.setConsent(true);
        mongoTemplate.insert(customerMessage3);

        Pageable pageable = PageRequest.of(0,3);
        Page<CustomerMessage> page0=customerMessageRepository.findByCustomerIdAndConsentOrderByDateDesc(customerid,true,pageable);
        Assertions.assertTrue(page0.get().count()==1 && page0.get().findFirst().get().getConsent());
    }

    @Test
    void findByLanguageAndConsentOrderByDateDesc_checkMatching(){
        //Setup for the test
        String language="IT";
        //Matching document
        customerMessage1.setLanguage(language);
        customerMessage1.setConsent(true);
        mongoTemplate.insert(customerMessage1);
        //Not matching document
        customerMessage1.setLanguage(language);
        customerMessage2.setConsent(false);
        mongoTemplate.insert(customerMessage2);
        //Not matching document
        customerMessage3.setLanguage("notmatchinglanguage");
        customerMessage3.setConsent(true);
        mongoTemplate.insert(customerMessage3);

        Pageable pageable = PageRequest.of(0,3);
        Page<CustomerMessage> page0=customerMessageRepository.findByLanguageAndConsentOrderByDateDesc(language,true,pageable);
        Assertions.assertTrue(page0.get().count()==1 && page0.get().findFirst().get().getLanguage().equalsIgnoreCase(language));
    }

    @Test
    void findByCustomerIdAndLanguageAndConsentOrderByDateDesc_checkMatching(){
        //Setup for the test
        String language="IT";
        String customerid="Achille";
        //Matching document
        customerMessage1.setCustomerId(customerid);
        customerMessage1.setLanguage(language);
        customerMessage1.setConsent(true);
        mongoTemplate.insert(customerMessage1);
        //Not matching document
        customerMessage2.setCustomerId(customerid);
        customerMessage1.setLanguage(language);
        customerMessage2.setConsent(false);
        mongoTemplate.insert(customerMessage2);
        //Not matching document
        customerMessage3.setCustomerId(customerid);
        customerMessage3.setLanguage("notmatchinglanguage");
        customerMessage3.setConsent(true);
        mongoTemplate.insert(customerMessage3);

        Pageable pageable = PageRequest.of(0,3);
        Page<CustomerMessage> page0=customerMessageRepository.findByCustomerIdAndLanguageAndConsentOrderByDateDesc(customerid,language,true,pageable);
        Assertions.assertTrue(page0.get().count()==1 &&
                page0.get().findFirst().get().getLanguage().equalsIgnoreCase(language) &&
                page0.get().findFirst().get().getCustomerId().equalsIgnoreCase(customerid));
    }

    @Test
    void findByConsentOrderByDateDesc_checkMatchingAndOrdering(){
        //Matching document - Oldest
        customerMessage1.setConsent(true);
        mongoTemplate.insert(customerMessage1);
        //Not matching document
        customerMessage2.setConsent(false);
        mongoTemplate.insert(customerMessage2);
        //Matching document - Newest
        customerMessage3.setConsent(true);
        mongoTemplate.insert(customerMessage3);

        Pageable pageable = PageRequest.of(0,3);
        Page<CustomerMessage> page0=customerMessageRepository.findByConsentOrderByDateDesc(true,pageable);
        List<Date> dateElement=new ArrayList<>();
        page0.forEach(element -> dateElement.add(element.getDate()));
        Assertions.assertTrue(page0.get().count()==2 &&
                dateElement.get(0).after(dateElement.get(1)));
    }


}