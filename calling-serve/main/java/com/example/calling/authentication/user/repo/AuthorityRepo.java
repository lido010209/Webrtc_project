package com.example.calling.authentication.user.repo;

import com.example.calling.authentication.user.document.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepo extends MongoRepository<Authority, String> {
    Optional<Authority> findByRole(String role);
    boolean existsByRole(String role);
}
