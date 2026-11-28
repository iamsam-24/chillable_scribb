package com.zenz.chillable.service;

import com.zenz.chillable.dto.request.QuestionRequest;
import com.zenz.chillable.dto.response.QuestionResponse;
import com.zenz.chillable.repository.QuestionRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private QuestionRespository questionRespository;



}
