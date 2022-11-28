package com.classMetabus.web.Admin.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateCertificateResponse {
    private Integer certificateId;
    private String directory;
}
