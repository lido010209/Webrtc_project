//package com.example.calling.authentication.user.document;
//
//import com.example.calling.baseEntity.BaseEntity;
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Document(collection = "authority")
//@Builder
//public class Authority extends BaseEntity {
//    private String role;
//    private final Set<UserDocument> users = new HashSet<>();
//}
