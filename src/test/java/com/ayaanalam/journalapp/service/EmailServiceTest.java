package com.ayaanalam.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendSimpleMail("nitishkumar.nk0201@gmail.com",
                "Testing",
                "Just a testing mail for java mail sender");
    }
}
