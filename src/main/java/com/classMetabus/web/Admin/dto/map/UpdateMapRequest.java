package com.classMetabus.web.Admin.dto.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateMapRequest {
    private Integer id;
    private String name;
    private Integer type;
    private Integer maxUser;
}
