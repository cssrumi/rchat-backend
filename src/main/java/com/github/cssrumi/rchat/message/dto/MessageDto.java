package com.github.cssrumi.rchat.message.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageDto {

    @NotEmpty
    public String id;
    @NotEmpty
    public String sendBy;
    @NotNull
    public Long sendAt;
    @NotEmpty
    public String message;

}
