package com.classMetabus.web.Admin.dto.ip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateIpRequest {
    private String address;
    private String name;
    private Integer maxUser;
}
