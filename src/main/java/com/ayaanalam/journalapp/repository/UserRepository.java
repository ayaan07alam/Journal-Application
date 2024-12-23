package com.ayaanalam.journalapp.repository;

import com.ayaanalam.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
    //ye method hamne isliye banayav kyuki hame usr find krna h uske name se naaki id se aur ye method already present nahi tha mongo repository me isiliye
    void deleteByUserName(String userName);
}
