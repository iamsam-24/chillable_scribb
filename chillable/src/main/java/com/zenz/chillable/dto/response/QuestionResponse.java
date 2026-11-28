package com.zenz.chillable.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class QuestionResponse {

    private String correct_ans;

    private LocalDateTime created_at;
}
