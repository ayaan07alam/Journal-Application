package com.ayaanalam.journalapp.scheduler;

import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.repository.UserRepositoryImpl;
import com.ayaanalam.journalapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    public void fetchUserAndSendSaMail(){
        List<User> userForSentimentAnalysis = userRepository.getUserForSentimentAnalysis();
        for(User user : userForSentimentAnalysis){

        }
    }
}
