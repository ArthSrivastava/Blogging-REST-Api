package com.thesnoozingturtle.bloggingrestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts() {
        return List.of(SecurityContext.builder().securityReferences(sf()).build());
    }

    private List<SecurityReference> sf() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Blogging REST APIs", "This project is developed by Arth Srivastava", "1.0", "Terms of Service",
                new Contact("Arth", "https://www.linkedin.com/in/arth-srivastava/", "arthex254@gmail.com"), "License of Api", "Api License URL", Collections.emptyList());
    }

}
