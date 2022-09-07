package com.classMetabus.web.Admin.dto.user;

import java.time.LocalDateTime;

public interface AbsentClassInfoProjectionInterface {
    Integer getId();
    String getName();
    String getLoginId();
    String getEmail();
    String getDepartment();
    Integer getParticipationLevel();

    //Boolean absentYN();
    LocalDateTime getAbsentDateTime();
    Boolean getLateYN();
}
