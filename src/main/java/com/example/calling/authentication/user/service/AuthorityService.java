//package com.example.calling.authentication.user.service;
//
//import com.example.calling.authentication.user.document.Authority;
//import com.example.calling.authentication.user.repo.AuthorityRepo;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthorityService {
//    private final AuthorityRepo authorityRepo;
//    private static final String[] authorities={
//            "ROLE_ADMIN", "ROLE_USER", "ROLE_VIEWER"
//    };
//
//    public AuthorityService(AuthorityRepo authorityRepo) {
//        this.authorityRepo = authorityRepo;
//        for (String authority: authorities){
//            if (!authorityRepo.existsByRole(authority))
//                authorityRepo.save(Authority.builder().role(authority).build());
//        }
//    }
//}
