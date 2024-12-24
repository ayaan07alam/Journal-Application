package com.ayaanalam.journalapp.service;

// This is for the authentication purpose

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to find user: {}", username); // Log the input username
       User user = userRepository.findByUserName(username);

        if(user != null) {
            log.debug("User found: {}", user.getUserName()); // Log successful user retrieval
         return  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        log.debug("User not found: {}", username); // Log if user is not found
        throw new UsernameNotFoundException("User not found : " + username);
    }

}

/*

        if(user != null) {
UserDetails userDetails =   org.springframework.security.core.userdetails.User.builder()
        .username(user.getUserName())
        .password(user.getPassword())
        .roles(user.getRoles().toArray(new String[0]))
        .build();
          return userDetails;
        }*/
