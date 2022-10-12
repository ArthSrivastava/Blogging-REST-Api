package com.thesnoozingturtle.bloggingrestapi;

import com.thesnoozingturtle.bloggingrestapi.config.AppConstants;
import com.thesnoozingturtle.bloggingrestapi.entities.Role;
import com.thesnoozingturtle.bloggingrestapi.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class BloggingRestApiApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(BloggingRestApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) {
//        System.out.println(this.passwordEncoder.encode("abc"));
        Role admin = new Role();
        admin.setId(AppConstants.ADMIN_USER);
        admin.setName("ROLE_ADMIN");

        Role normal = new Role();
        normal.setId(AppConstants.NORMAL_USER);
        normal.setName("ROLE_NORMAL");
        this.roleRepo.save(admin);
        this.roleRepo.save(normal);
    }
}
