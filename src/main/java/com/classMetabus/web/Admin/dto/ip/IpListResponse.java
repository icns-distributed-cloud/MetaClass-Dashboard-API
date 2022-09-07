package com.classMetabus.web.Admin.dto.ip;

import com.classMetabus.web.Admin.domain.IP;
import com.classMetabus.web.Admin.domain.Server;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IpListResponse {
    private Integer id;
    private String address;
    private String name;
    private Integer maxUser;

    public IpListResponse(IP ip) {
        this.id = ip.getId();
        this.address = ip.getAddress();
        this.name = ip.getName();
        this.maxUser = ip.getMaxUser();
    }
}
