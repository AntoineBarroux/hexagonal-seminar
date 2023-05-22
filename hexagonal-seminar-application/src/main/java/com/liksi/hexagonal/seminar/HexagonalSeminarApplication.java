package com.liksi.hexagonal.seminar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.liksi.hexagonal.seminar")
public class HexagonalSeminarApplication {
    public static void main(String[] args) {
        SpringApplication.run(HexagonalSeminarApplication.class, args);
    }
}
