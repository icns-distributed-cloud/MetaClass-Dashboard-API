package com.classMetabus.web.Admin.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendMessageRequest {
    private Integer studentId;
    private Integer lectureId;
}
