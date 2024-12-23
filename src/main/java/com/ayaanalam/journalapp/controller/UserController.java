package com.ayaanalam.journalapp.controller;

import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.repository.UserRepository;
import com.ayaanalam.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @PutMapping("/{userName}")   //This is wrong as we can cannot send userName directly tp Path Parameter
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(updatedUser.getUserName());
            userInDb.setPassword(updatedUser.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
