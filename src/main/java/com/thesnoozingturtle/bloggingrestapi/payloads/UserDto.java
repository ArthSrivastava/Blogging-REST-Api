package com.thesnoozingturtle.bloggingrestapi.payloads;

import com.thesnoozingturtle.bloggingrestapi.entities.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    //This is same as user entity, it is used so that the entity class is not directly exposed to the APIs
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, message = "Name should be minimum of 3 characters!")
    private String name;

    @Email(message = "Email is not valid!")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 3, max = 10, message = "Password length must be between 3 to 10 characters")
    private String password;

    @NotEmpty(message = "About cannot be empty")
    private String about;

    private Set<CommentDto> comments = new HashSet<>();
}
