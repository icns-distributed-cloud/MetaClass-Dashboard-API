package com.classMetabus.web.Admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
    private String loginId;
    private Integer userMode;
    private String password;
}
