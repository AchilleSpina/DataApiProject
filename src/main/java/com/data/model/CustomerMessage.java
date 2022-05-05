package com.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document("CustomerMessages")
public class CustomerMessage {
    String customerId;
    String dialogId;
    String text;
    String language;
}
