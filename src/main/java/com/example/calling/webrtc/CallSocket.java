package com.example.calling.webrtc;

import com.example.calling.webrtc.dto.CallRequest;
import com.example.calling.webrtc.dto.CallResponse;
import com.example.calling.webrtc.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CallSocket {
    private final SocketService callService;
    @MessageMapping("/signaling")
    @SendTo("/topic/signaling")
    public CallResponse startCall(CallRequest dto){
        return callService.startCall(dto);
    }

    @MessageMapping("/on-call")
    @SendTo("/topic/on-call")
    public CallResponse onCall(CallResponse dto){
        return callService.receiveCall(dto.getId());
    }

    @MessageMapping("/reject")
    @SendTo("/topic/reject")
    public CallResponse reject(CallResponse dto){
        return callService.rejectCall(dto.getId());
    }
    @MessageMapping("/miss-call")
    @SendTo("/topic/miss-call")
    public CallResponse missCall(CallResponse dto){
        return callService.missCall(dto.getId());
    }

    @MessageMapping("/hangup")
    @SendTo("/topic/hangup")
    public CallResponse hangup(CallResponse dto){
        return callService.hangup(dto.getId());
    }

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String createOffer(@Payload String offer){
        return offer;
    }

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String createAnswer(@Payload String answer){
        return answer;
    }

    @MessageMapping("/candidate")
    @SendTo("/topic/candidate")
    public String candidate(@Payload String candidate){
        return candidate;
    }
}
