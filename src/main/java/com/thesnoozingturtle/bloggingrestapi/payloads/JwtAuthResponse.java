package com.thesnoozingturtle.bloggingrestapi.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
}
