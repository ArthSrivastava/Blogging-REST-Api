package com.thesnoozingturtle.bloggingrestapi.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDto {
    private int commentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto user;
    private String content;
}
