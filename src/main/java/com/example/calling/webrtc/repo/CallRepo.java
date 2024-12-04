package com.example.calling.webrtc.repo;


import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.webrtc.entity.Call;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CallRepo extends MongoRepository<Call, String> {
    boolean existsByCalleeAndStatus(UserDocument callee, Call.Status status);
    Optional<Call> findByCalleeAndStatus(UserDocument callee, Call.Status status);
    boolean existsByCalleeAndCallerAndStatus(UserDocument callee, UserDocument caller, Call.Status status);
    Optional<Call> findByCalleeAndCallerAndStatus(UserDocument callee, UserDocument caller, Call.Status status);

    @Query("{ $and: [ " +
            "{ $or: [ { 'status': 'Connecting' }, { 'status': 'OnCall' } ] }, " +
            "{ $or: [ { 'caller': ?0 }, { 'callee': ?0 } ] } ] }")
    Optional<Call> checkConnect(UserDocument user);
}
