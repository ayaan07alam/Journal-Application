package com.ayaanalam.journalapp.controller;

import com.ayaanalam.journalapp.cache.AppCache;
import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createNewUser(@RequestBody User user){
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("clear-app-cache")
    public ResponseEntity<?> clearCache(){
        appCache.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
