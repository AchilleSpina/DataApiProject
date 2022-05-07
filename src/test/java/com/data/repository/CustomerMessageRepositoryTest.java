package com.data.repository;

import com.data.model.CustomerMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;
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
        customerMessage2.setDialogId(dialogidTest);
        customerMessage3.setDialogId(dialogidTest);
        mongoTemplate.insert(customerMessage1);
        mongoTemplate.insert(customerMessage2);
        mongoTemplate.insert(customerMessage3);
        Integer documentDeleted=customerMessageRepository.deleteByDialogId("DialogIdNotPresent");
        Assertions.assertEquals(documentDeleted,0);

    }

}