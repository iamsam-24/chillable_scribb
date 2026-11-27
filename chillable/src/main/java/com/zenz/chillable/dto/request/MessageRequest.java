package com.zenz.chillable.dto.request;


import com.zenz.chillable.entity.Room;
import com.zenz.chillable.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {

    private Room rooom;

    private UUID userId;

   private String content;

   private String message_type;
}
