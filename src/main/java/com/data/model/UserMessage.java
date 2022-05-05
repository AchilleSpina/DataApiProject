package com.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document("UserMessages")
public class UserMessage {
    String customerId;
    String dialogId;
    String text;
    String language;
    //TODO ADD date and its management
    //Date date;
}
