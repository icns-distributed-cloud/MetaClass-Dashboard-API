package com.classMetabus.web.Admin.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Students {
    private Integer studentId;
    private String studentName;
    private String LoginId;
}
