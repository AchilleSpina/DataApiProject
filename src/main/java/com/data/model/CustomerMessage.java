package com.data.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@Document("CustomerMessages")
public class CustomerMessage {
    @Schema(description = "Customer identifier", example = "customer1345")
    String customerId;
    @Schema(description = "Dialog identifier", example = "dialog1345")
    String dialogId;
    @Schema(description = "Text of message", example = "the text from the customer")
    String text;
    @Schema(description = "Language of message", example = "EN")
    String language;
    @Schema(description = "Consent", example = "true")
    @Builder.Default
    Boolean consent=false;
    @Schema(description = "Date of push",type = "Date", example = "2022-05-08T15:55:24")
    @CreatedDate
    Date date;
}
