package com.thesnoozingturtle.bloggingrestapi.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Set<CommentDto> comments = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<UserDto> likedBy = new HashSet<>();
    private long likesCount;
}
