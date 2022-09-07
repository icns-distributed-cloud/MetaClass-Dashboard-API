package com.classMetabus.web.Admin.dto.absentParticipation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentLectureRequest {
    private Integer studentId;
    private Integer lectureId;
}
