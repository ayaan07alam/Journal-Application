    package com.ayaanalam.journalapp.controller;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    import com.ayaanalam.journalapp.entity.User;
    import com.ayaanalam.journalapp.service.UserService;
    import org.bson.types.ObjectId;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
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

        @GetMapping
        public ResponseEntity<?> getAllJournalEntryOfUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User connectedUser = userService.findByUserName(userName);
            List<JournalEntry> all = connectedUser.getJournalEntries()  ;

            if (all != null && !all.isEmpty() ) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @GetMapping("id/{myId}")
        public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); //database se user ki id leke compare  kare ki id h ki nhi journal entry ki fir uske baad display bbhi karde
            if(!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PostMapping
        public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {    // ? =  wild card pattern used to return different kinf of the data type
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                journalEntryService.saveEntry(myEntry,userName);
                return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("id/{myId}")
        public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean removed = journalEntryService.deleteById(myId, userName);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("id/{myId}")
        public ResponseEntity<?> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry myNewEntry) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); //database se user ki id leke compare  kare ki id h ki nhi journal entry ki fir uske baad display bbhi karde
            if(!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    JournalEntry oldJournalEntry = journalEntry.get();
                    oldJournalEntry.setTitle(myNewEntry.getTitle() != null && !myNewEntry.getTitle().equals("") ? myNewEntry.getTitle() : oldJournalEntry.getTitle());
                    oldJournalEntry.setContent(myNewEntry.getContent() != null && !myNewEntry.getContent().equals("") ? myNewEntry.getContent() : oldJournalEntry.getContent());
                    journalEntryService.saveEntry(oldJournalEntry);
                    return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }