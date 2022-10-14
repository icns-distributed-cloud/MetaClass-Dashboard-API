package com.classMetabus.web.Admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChangePasswordRequest {
    private Integer id;
    private String password;
    private Integer userMode;
}
