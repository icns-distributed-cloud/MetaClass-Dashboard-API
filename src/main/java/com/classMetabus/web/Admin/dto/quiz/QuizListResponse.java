package com.classMetabus.web.Admin.dto.quiz;

import com.classMetabus.web.Admin.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QuizListResponse {
    private Integer id;
    private String name;
    private Integer instructorId;

    public QuizListResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.name = quiz.getName();
        this.instructorId = quiz.getInstructor().getId();
    }
}
