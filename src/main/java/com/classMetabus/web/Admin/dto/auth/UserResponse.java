package com.classMetabus.web.Admin.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String email;

    private String name;

    private String phone;

    private Long id;
}