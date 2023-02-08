package com.thesnoozingturtle.bloggingrestapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("\n\n\n Inside access denied handler \n\n\n");

        ApiResponse apiResponse = new ApiResponse(accessDeniedException.getMessage(), false);
        response.setContentType("application/json");
        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, apiResponse);
        outputStream.flush();
    }
}
