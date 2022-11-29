package com.classMetabus.web.Admin.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCetificateByIdRequest {
    private Integer instructorId;
    private Integer certificateId;
    private String certificateName;
}
