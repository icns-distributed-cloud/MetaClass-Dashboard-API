package com.classMetabus.web.Admin.dto.user;

import io.swagger.models.auth.In;

import java.time.LocalDateTime;

public interface InstructorIdcntProjectionInterface {
    Integer getId();
    String getName();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    Integer getContentId();
    String getContentName();
    Integer getInstructorId();
    String getInstructorName();
    Integer getMapId();
    Integer getMapMaxUser();
    Integer getMapType();
    String getMapName();
    Integer getCountUser();
    Integer getQuizId();
    String getQuizName();
    Integer getQuizScore();
    Integer getParticipationLevel();
    LocalDateTime getAbsentTime();
    Boolean getIsAutoClass();
}
