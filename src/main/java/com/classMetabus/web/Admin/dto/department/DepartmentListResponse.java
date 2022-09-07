package com.classMetabus.web.Admin.dto.department;

import com.classMetabus.web.Admin.domain.Department;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DepartmentListResponse {
    private Integer id;
    private String name;

    public DepartmentListResponse(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }
}
