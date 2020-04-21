package com.github.cssrumi.rchat.message.dto;

import javax.validation.constraints.NotEmpty;

public class MessageRequest {

    @NotEmpty
    public String message;

}
