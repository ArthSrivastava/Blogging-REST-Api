package com.thesnoozingturtle.bloggingrestapi.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<PostDto> content;
    private int pageNumber;
    private int numberOfElementsOnSinglePage;
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
}
