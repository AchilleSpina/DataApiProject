package com.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

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
    @CreatedDate
    Date date;
}
