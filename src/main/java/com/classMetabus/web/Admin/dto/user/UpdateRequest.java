package com.classMetabus.web.Admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateRequest {
    private Integer id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Integer userMode;
    private Integer departmentId;
}
