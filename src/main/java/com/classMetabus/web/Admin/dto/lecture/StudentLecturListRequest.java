package com.classMetabus.web.Admin.dto.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentLecturListRequest {
    private Integer studentId;
    private LocalDate startDate;
    private LocalDate endDate;
}
