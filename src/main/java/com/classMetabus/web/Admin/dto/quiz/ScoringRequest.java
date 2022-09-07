package com.classMetabus.web.Admin.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScoringRequest {
    private Integer quizId;
    private Integer studentListId;
    private List<Answer> answers;
}
