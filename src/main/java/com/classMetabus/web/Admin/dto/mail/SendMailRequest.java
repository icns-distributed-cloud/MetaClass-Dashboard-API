package com.classMetabus.web.Admin.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendMailRequest {
    private Integer instructorId;
    private String context;
    private Integer studentId;
    private Integer lectureId;
}
