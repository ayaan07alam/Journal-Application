package com.ayaanalam.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ayaanalam.journalapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ayaanalam.journalapp.entity.JournalEntry;
import com.ayaanalam.journalapp.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User findUser = userService.findByUserName(userName); // can also be done in controller but for best practices we have written it here
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry); // here our journal entry saved to the database journaldb and we have assigned it to the variable named "saved"
            findUser.getJournalEntries().add(saved);
            userService.saveUser(findUser);
        } catch (Exception e) {
            throw new RuntimeException("Error saving journal entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
            journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId){
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User findUserToDelete = userService.findByUserName(userName);
             removed = findUserToDelete.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(findUserToDelete);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Error deleting journal entry", e);
            throw new RuntimeException("An error occured while deleting the entry",e);
        }
        return removed;

    }

    public List <JournalEntry> findByUserName(String userName){
        // Find the user by username
        User user = userService.findByUserName(userName);

        // Return the list of journal entries associated with this user
        return user.getJournalEntries();
    }
}
