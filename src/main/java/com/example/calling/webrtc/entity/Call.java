package com.example.calling.webrtc.entity;

import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.baseEntity.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Call extends BaseEntity {
    private UserDocument caller;
    private UserDocument callee;
    private Status status;
    public enum Status {
        Connecting, OnCall, Reject, MissCall, Hangup
    }
}
