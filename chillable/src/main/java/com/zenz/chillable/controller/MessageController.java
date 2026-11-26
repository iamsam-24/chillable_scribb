package com.zenz.chillable.controller;


import com.zenz.chillable.dto.request.MessageRequest;
import com.zenz.chillable.dto.response.MessageResponse;
import com.zenz.chillable.entity.Message;
import com.zenz.chillable.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Message> get(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.get(id));
    }



}
