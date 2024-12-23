package com.ayaanalam.journalapp.controller;

// This is called public controller because this can be accessed by others without the authentication
import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Application is running";
    }

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        userService.saveNewUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
