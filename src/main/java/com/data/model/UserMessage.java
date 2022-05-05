package com.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserMessage {
    String customerId;
    String dialogId;
    String text;
    String language;
    //TODO ADD date
    //Date date;
}
