package com.example.calling.webrtc;

import com.example.calling.webrtc.dto.CallRequest;
import com.example.calling.webrtc.dto.CallResponse;
import com.example.calling.webrtc.service.HttpService;
import com.example.calling.webrtc.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("call")
public class CallHttp {
    private final HttpService callService;
    private final SocketService socketService;
//    @PostMapping
//    public CallResponse newCall(@RequestBody CallRequest request){
//        return socketService.startCall(request);
//    }
    @DeleteMapping("{callId}")
    public void deleteCall(@PathVariable("callId") String callId){
        callService.cancelCall(callId);
    }
    @GetMapping("{callId}")
    public CallResponse getCall(@PathVariable("callId") String callId){
        return callService.getCall(callId);
    }
    @PutMapping("{callId}")
    public CallResponse missCall(@PathVariable("callId") String callId){
        return callService.missCall(callId);
    }
    @GetMapping("check-available/{calleeId}")
    public boolean checkConnect(@PathVariable("calleeId") String calleeId){
        return callService.checkAvailable(calleeId);
    }

    @GetMapping("miss-call")
    public CallResponse markUser(){
        return callService.markMissCall();
    }
    @GetMapping("miss-call/admin/{callerId}")
    public CallResponse markAdmin(@PathVariable("callerId") String callerId){
        return callService.markAdmin(callerId);
    }
    @GetMapping("miss-call/admin")
    public boolean markAdmin(){
        return callService.mark();
    }
}
