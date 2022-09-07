package com.classMetabus.web.Admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeleteRequest {
    private String loginId;
    private Integer userMode;
}
