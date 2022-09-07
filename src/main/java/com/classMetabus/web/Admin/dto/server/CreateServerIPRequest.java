package com.classMetabus.web.Admin.dto.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateServerIPRequest {
    private Integer ipId;
    private Integer lectureId;
}
