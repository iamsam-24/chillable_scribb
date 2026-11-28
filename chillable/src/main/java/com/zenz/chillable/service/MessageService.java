package com.zenz.chillable.service;

import com.zenz.chillable.dto.request.MessageRequest;
import com.zenz.chillable.dto.response.MessageResponse;
import com.zenz.chillable.entity.Message;
import com.zenz.chillable.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageResponse create(MessageRequest request){

        Message message=new Message();

        message.setContent(request.getContent());
        message.setCreated_at(LocalDateTime.now());
        message.setRoom(request.getRooom());
        message.setUser(request.getRooom().getUser());

        messageRepository.save(message);

        return MessageResponse.from(message);

    }

    @Transactional
    public Message get(UUID id){
        return messageRepository.findById(id).orElseThrow(()-> new RuntimeException("Message not found"));
    }
}
