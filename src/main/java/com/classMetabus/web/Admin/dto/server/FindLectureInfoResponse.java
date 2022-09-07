package com.classMetabus.web.Admin.dto.server;

import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.domain.Server;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FindLectureInfoResponse {
    private Integer lecturId;
    private String lectureName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lectureStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lectureEndTime;

    public FindLectureInfoResponse(Lecture lecture) {
        this.lecturId = lecture.getId();
        this.lectureName = lecture.getName();
        this.lectureStartTime = lecture.getStartTime();
        this.lectureEndTime = lecture.getEndTime();
    }
}
