package com.classMetabus.web.Admin.dto.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateMapRequest {
    private Integer type;
    private String name;
    private Integer maxUser;
    private Integer instructorId;
}
