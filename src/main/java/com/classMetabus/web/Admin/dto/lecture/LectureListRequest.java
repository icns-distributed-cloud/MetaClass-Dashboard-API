package com.classMetabus.web.Admin.dto.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LectureListRequest {
    private Integer instructorId;
    private LocalDate startDate;
    private LocalDate endDate;
}
