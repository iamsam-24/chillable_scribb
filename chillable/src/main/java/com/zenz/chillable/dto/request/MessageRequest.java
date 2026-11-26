package com.zenz.chillable.dto.request;


import com.zenz.chillable.entity.Room;
import com.zenz.chillable.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {

    private Room rooom;

    private User user;

   private String content;

   private String message_type;
}
