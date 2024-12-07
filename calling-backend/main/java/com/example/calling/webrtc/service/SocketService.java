package com.example.calling.webrtc.service;

import com.example.calling.authentication.component.UserComponent;
import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.exception.CustomException;
import com.example.calling.webrtc.dto.CallRequest;
import com.example.calling.webrtc.dto.CallResponse;
import com.example.calling.webrtc.entity.Call;
import com.example.calling.webrtc.repo.CallRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final CallRepo callRepo;
    private final UserComponent userComponent;
    public CallResponse startCall(CallRequest dto){
        UserDocument caller = userComponent.userById(dto.getCallerId());
        UserDocument callee = userComponent.userById(dto.getCalleeId());
        Call newCall = Call.builder()
                .callee(callee)
                .caller(caller)
                .status(Call.Status.Connecting)
                .build();
        return CallResponse.dto(callRepo.save(newCall));
    }

    public CallResponse receiveCall(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        call.setStatus(Call.Status.OnCall);
        return CallResponse.dto(callRepo.save(call));
    }
    public CallResponse rejectCall(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        call.setStatus(Call.Status.Reject);
        return CallResponse.dto(callRepo.save(call));
    }

    @Transactional
    public CallResponse missCall(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        call.setStatus(Call.Status.MissCall);
        return CallResponse.dto(callRepo.save(call));
    }


    public CallResponse hangup(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        call.setStatus(Call.Status.Hangup);
        return CallResponse.dto(callRepo.save(call));
    }
}
