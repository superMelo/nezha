package com.nezha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.nezha")
public class NezhaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NezhaApplication.class, args);
    }
}
