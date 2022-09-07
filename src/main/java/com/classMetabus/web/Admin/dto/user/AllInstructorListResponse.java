package com.classMetabus.web.Admin.dto.user;

import com.classMetabus.web.Admin.domain.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllInstructorListResponse {
    private Integer id;
    private String name;

    public AllInstructorListResponse(Instructor instructor) {
        this.id = instructor.getId();
        this.name = instructor.getName();
    }
}
