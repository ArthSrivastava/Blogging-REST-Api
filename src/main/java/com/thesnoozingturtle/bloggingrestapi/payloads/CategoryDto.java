package com.thesnoozingturtle.bloggingrestapi.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int categoryId;

    @NotBlank
    @Size(min = 4, message = "Title must be at least 4 characters long!")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Description must be at least 10 characters long!")
    private String categoryDescription;
}
