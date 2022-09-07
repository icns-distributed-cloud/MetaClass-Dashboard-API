package com.classMetabus.web.Admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {
    private Integer id;
    private String name;
    private String loginId;
    private String email;
    private String phone;
    private String departmentName;
}
