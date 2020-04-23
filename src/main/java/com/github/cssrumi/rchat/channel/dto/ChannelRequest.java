package com.github.cssrumi.rchat.channel.dto;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class ChannelRequest {

    @NotEmpty
    @Length(min = 5, max = 20, message = "Channel name can't be shorter than {min} and longer than {max} characters.")
    public String name;

}
