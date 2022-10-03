package com.thesnoozingturtle.bloggingrestapi.payloads;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    //This is same as user entity, it is used so that the entity class is not directly exposed to the APIs
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;
}
