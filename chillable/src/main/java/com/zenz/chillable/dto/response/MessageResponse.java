package com.zenz.chillable.dto.response;


import com.zenz.chillable.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {

    private String content;

    private String message_type;

    public static MessageResponse from (Message message){
       return new MessageResponse(
               message.getContent(),
               message.getMessage_type()
       );
    }


}
