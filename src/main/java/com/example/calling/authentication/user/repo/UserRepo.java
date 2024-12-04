package com.example.calling.authentication.user.repo;

import com.example.calling.authentication.user.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserDocument, String> {
    boolean existsByUsername(String username);
    Optional<UserDocument> findByUsername(String username);
}
