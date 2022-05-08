package com.data.repository;

import com.data.model.CustomerMessage;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomerMessageRepositoryCustomImpl implements CustomerMessageRepositoryCustom{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Integer updateConsentByDialogId(String dialogId, Boolean consent) {
        Query query = new Query();
        //Matching criteria
        query.addCriteria(Criteria.where("dialogId").is(dialogId));
        //Field to update
        Update update = new Update();
        update.set("consent", consent);
        UpdateResult updateResult=mongoTemplate.updateMulti(query, update, CustomerMessage.class);
        Long modified = updateResult.getModifiedCount();
        return modified.intValue();
    }
}