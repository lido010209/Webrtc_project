package com.example.calling.webrtc.dto;

import com.example.calling.webrtc.entity.Call;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallResponse {
    private String id;
    private String status;
    private String callerName;
    private String callerImg;
    private String callerId;
    private String calleeName;
    private String calleeImg;
    private String calleeId;
    public static CallResponse dto (Call call){
        CallResponse callResponse = CallResponse.builder()
                .id(call.getId())
                .callerId(call.getCaller().getId())
                .callerName(call.getCaller().getName())
                .callerImg(call.getCaller().getAvatar())
                .calleeId(call.getCallee().getId())
                .calleeName(call.getCallee().getName())
                .calleeImg(call.getCallee().getAvatar())
                .status(call.getStatus().toString())
                .build();
        return callResponse;
    }
}
