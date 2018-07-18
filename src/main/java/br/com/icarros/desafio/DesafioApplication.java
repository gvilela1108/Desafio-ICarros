package br.com.icarros.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.icarros.desafio")
public class DesafioApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesafioApplication.class, args);
    }
}
