package com.classMetabus.web.Admin.dto.certificate;

import com.classMetabus.web.Admin.domain.Certificate;
import com.classMetabus.web.Admin.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CertificateListResponse {
    private Integer id;
    private String name;
    private String directory;

    public CertificateListResponse(Certificate certificate) {
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.directory = certificate.getDirectory();
    }
}
