package com.classMetabus.web.Admin.dto.user;

import com.classMetabus.web.Admin.domain.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllStudentListResponse {
    private Integer id;
    private String loginId;
    private String email;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime joinDate;
    private String departmentName;

    private Integer status;
    public AllStudentListResponse(Student student) {
        this.id = student.getId();
        this.loginId = student.getLoginId();
        this.email = student.getEmail();
        this.name = student.getName();
        this.joinDate = student.getJoinDate();
        this.departmentName = student.getDepartment().getName();
        this.status = student.getStatus();
    }
}
