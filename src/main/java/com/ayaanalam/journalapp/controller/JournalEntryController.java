package com.ayaanalam.journalapp.controller;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ayaanalam.journalapp.entity.User;
import com.ayaanalam.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayaanalam.journalapp.entity.JournalEntry;
import com.ayaanalam.journalapp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String userName) {
        User connectedUser = userService.findByUserName(userName);
//        List<JournalEntry> all = journalEntryService.getAll(); older
        List<JournalEntry> all = connectedUser.getJournalEntries()  ;

        if (all != null && !all.isEmpty() ) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {    // ? =  wild card pattern used to return different kinf of the data type
        try {
//            myEntry.setDate(LocalDateTime.now()); //done in service class
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry myNewEntry,
            @PathVariable String userName
    ) {
        JournalEntry oldJournalEntry = journalEntryService.findById(myId).orElse(null);
        if (oldJournalEntry != null) {
            oldJournalEntry.setTitle(myNewEntry.getTitle() != null && !myNewEntry.getTitle().equals("") ? myNewEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(myNewEntry.getContent() != null && !myNewEntry.getContent().equals("") ? myNewEntry.getContent() : oldJournalEntry.getContent());
            journalEntryService.saveEntry(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}