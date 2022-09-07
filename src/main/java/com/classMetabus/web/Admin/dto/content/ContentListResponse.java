package com.classMetabus.web.Admin.dto.content;


import com.classMetabus.web.Admin.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContentListResponse {
    private Integer id;
    private String name;
    private String directory;
    private String playTime;

    public ContentListResponse(Content content) {
        this.id = content.getId();
        this.name = content.getName();
        this.directory = content.getDirectory();
        this.playTime = content.getPlayTime();
    }
}
