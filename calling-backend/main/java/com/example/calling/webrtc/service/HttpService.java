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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpService {
    private final CallRepo callRepo;
    private final UserComponent userComponent;
    public boolean checkAvailable(String calleeId){
        UserDocument callee = userComponent.userById(calleeId);
        if (callRepo.checkConnect(callee).isEmpty()){
            return true;
        }
        return false;
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

    public void cancelCall(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        callRepo.delete(call);
    }

    public CallResponse getCall(String callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "No exist this calling!!!")
        );
        return CallResponse.dto(call);
    }
    public CallResponse markMissCall(){
        Optional<Call> call= callRepo.findByCalleeAndCallerAndStatus(
                userComponent.userLogin(), userComponent.userById("6744726d7d1524d8fb7ee47b"), Call.Status.MissCall
        );
        if (call.isEmpty()) return CallResponse.builder().id(null).build();
        return CallResponse.dto(call.get());
    }
    public CallResponse markAdmin(String callerId){
        Optional<Call> call= callRepo.findByCalleeAndCallerAndStatus(
                userComponent.userLogin(), userComponent.userById(callerId), Call.Status.MissCall
        );
        if (call.isEmpty()) return CallResponse.builder().id(null).build();
        return CallResponse.dto(call.get());
    }
    public boolean mark(){
        return callRepo.existsByCalleeAndStatus(userComponent.userLogin(), Call.Status.MissCall);
    }
}
