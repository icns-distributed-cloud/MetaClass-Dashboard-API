package com.classMetabus.web.Admin.dto.map;

import com.classMetabus.web.Admin.domain.Department;
import com.classMetabus.web.Admin.domain.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MapListResponse {
    private Integer id;
    private String name;
    private Integer type;
    private Integer maxUser;

    public MapListResponse(Map map) {
        this.id = map.getId();
        this.name = map.getName();
        this.type = map.getType();
        this.maxUser = map.getMaxUser();
    }
}
