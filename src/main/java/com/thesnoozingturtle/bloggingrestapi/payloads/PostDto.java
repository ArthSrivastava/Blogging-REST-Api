package com.thesnoozingturtle.bloggingrestapi.payloads;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private int postId;
    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private CategoryDto category;
    private UserDto user;
}
