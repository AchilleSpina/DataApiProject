package com.data.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Message {
    @Schema(description = "Text of message", example = "the text from the customer")
    String text;
    @Schema(description = "Language of message", example = "EN")
    String language;
}
