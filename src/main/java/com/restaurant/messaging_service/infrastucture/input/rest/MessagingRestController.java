package com.restaurant.messaging_service.infrastucture.input.rest;

import com.restaurant.messaging_service.application.dto.NotifyClientRequestDto;
import com.restaurant.messaging_service.application.handler.IMessagingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Validated
public class MessagingRestController {

    private  final IMessagingHandler messagingHandler;

    @PostMapping("/notify")
    public ResponseEntity<Void> notifyClient(@RequestBody NotifyClientRequestDto notifyClientRequestDto) {
        messagingHandler.notifyClient(notifyClientRequestDto);
        return ResponseEntity.noContent().build();
    }
}
