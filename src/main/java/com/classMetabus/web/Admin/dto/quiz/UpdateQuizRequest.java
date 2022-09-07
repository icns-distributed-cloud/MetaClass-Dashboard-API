package com.classMetabus.web.Admin.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateQuizRequest {
    private Integer id;
    private String name;
    private List<QuizList> data;
}