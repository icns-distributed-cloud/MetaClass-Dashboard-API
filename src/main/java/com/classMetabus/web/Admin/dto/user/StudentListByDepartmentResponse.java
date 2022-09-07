package com.classMetabus.web.Admin.dto.user;

import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StudentListByDepartmentResponse {
    public Integer studentId;
    public String studentName;

    public StudentListByDepartmentResponse(Student student) {
        this.studentId = student.getId();
        this.studentName = student.getName();
    }
}
