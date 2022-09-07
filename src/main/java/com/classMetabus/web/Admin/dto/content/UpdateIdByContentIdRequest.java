package com.classMetabus.web.Admin.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateIdByContentIdRequest {
    private Integer instructorId;
    private Integer contentId;
    private String contentName;
    private String playTime;
}
