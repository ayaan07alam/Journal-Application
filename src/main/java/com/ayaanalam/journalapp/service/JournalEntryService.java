package com.ayaanalam.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ayaanalam.journalapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ayaanalam.journalapp.entity.JournalEntry;
import com.ayaanalam.journalapp.repository.JournalEntryRepository;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {
            User findUser = userService.findByUserName(userName); // can also be done in controller but for best practices we have written it here
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry); // here our journal entry saved to the database journaldb and we have assigned it to the variable named "saved"
            findUser.getJournalEntries().add(saved);
            userService.saveUser(findUser);

/*            basically in all these method, first journal entry saved in the database via our older native method,
            then we have saved that journal entry into the variable named "saved"
            after that we have used the object reference of User(Entity class) and with the help of its method we saved the journal entry top the list
            after that with the help of the userservice(basically for saving the data in database we everytime use the serviice) in order save the journal entry
            in the database of the user. With all these methods we successfully created a new entry in the user from journaldb  */
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

    public void deleteById(ObjectId id, String userName){
        User findUserToDelete = userService.findByUserName(userName);
        findUserToDelete.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(findUserToDelete);
        journalEntryRepository.deleteById(id);
    }
}
