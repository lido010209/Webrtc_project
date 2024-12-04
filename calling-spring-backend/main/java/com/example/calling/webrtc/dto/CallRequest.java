package com.example.calling.webrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CallRequest {
    private String callerId;
    private String calleeId;
}
