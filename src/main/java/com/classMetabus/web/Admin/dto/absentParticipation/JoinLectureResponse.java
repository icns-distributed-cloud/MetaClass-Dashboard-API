package com.classMetabus.web.Admin.dto.absentParticipation;

import com.classMetabus.web.Admin.domain.StudentList;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JoinLectureResponse {
    private Integer studentListId;
    private Integer quizId;
}
