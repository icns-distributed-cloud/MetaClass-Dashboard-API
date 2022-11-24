package com.classMetabus.web.Admin.dto.lecture;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateLectureRequest {
    private String name;
    private Integer instructorId;
    private Boolean isAutoClass;
    private Integer mapId;
    private Integer contentId;
    private Integer quizId;
    private List<StudentIdList> stulist;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    public static class StudentIdList {
        public Integer studentId;
    }
}

