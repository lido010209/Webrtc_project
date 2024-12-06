package com.example.calling.authentication.user.document;

import com.example.calling.baseEntity.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "user_account")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class UserDocument extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private String avatar;
//    @DBRef
//    private final Set<Authority> authorities= new HashSet<>();
    private final Set<String> authorityRoles = new HashSet<>();
}
