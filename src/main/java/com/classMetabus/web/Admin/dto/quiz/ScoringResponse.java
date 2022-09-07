package com.classMetabus.web.Admin.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScoringResponse {
    private Boolean success;
    private Integer quizScore;
}
