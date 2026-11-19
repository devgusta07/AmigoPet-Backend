//package com.petshop.petshop_api.integration;
//
//import com.petshop.petshop_api.service.EmailService;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import io.github.cdimascio.dotenv.Dotenv;
//
//@SpringBootTest
//public class EmailServiceIntegrationTest {
//
//    @Autowired
//    private EmailService emailService;
//
//
//    @DynamicPropertySource
//    static void configurarVariaveis(DynamicPropertyRegistry registry) {
//
//        Dotenv dotenv = Dotenv.load();
//
//        registry.add("spring.mail.username",
//                () -> dotenv.get("EMAIL_USERNAME"));
//
//        registry.add("spring.mail.password",
//                () -> dotenv.get("EMAIL_PASSWORD"));
//    }
//
//
//    @Test
//
//    void deveEnviarEmail() {
//        emailService.enviarEmail(
//                "sahlevadinha@gmail.com",
//                "Teste de envio - AmigoPet",
//                "Boa noite mae , estou testando meu programa que envia gmail bjs."
//        );
//    }
//
//}
