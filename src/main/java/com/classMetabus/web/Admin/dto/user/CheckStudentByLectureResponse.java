package com.classMetabus.web.Admin.dto.user;

import com.classMetabus.web.Admin.domain.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CheckStudentByLectureResponse {
    private Integer id;
    private String name;
    private String loginId;
    private String email;
    private String department;
    private Integer participationLevel;
    private Boolean absentYN;
    private Boolean lateYN;
}