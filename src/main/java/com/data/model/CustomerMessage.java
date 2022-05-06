package com.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Builder
@Document("CustomerMessages")
public class CustomerMessage {
    String customerId;
    String dialogId;
    String text;
    String language;
}
