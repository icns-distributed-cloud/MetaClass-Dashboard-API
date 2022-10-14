package com.classMetabus.web.Admin.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LectureListResponse {
    private Integer id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    private Integer contentId;
    private String contentName;
    private Integer countUser;
    private Integer instructorId;
    private String instructorName;
    private Integer mapId;
    private Integer mapMaxUser;
    private Integer mapType;
    private String mapName;
    private Integer quizId;
    private String quizName;
    private List<Students> students;
}

