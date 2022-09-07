package com.classMetabus.web.Admin.dto.server;

import com.classMetabus.web.Admin.domain.Server;
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
public class ServerIPListResponse {
    private Integer id;
    private Integer ipId;
    private String ipAddress;
    private String ipName;
    private Integer lectureId;
    private String lectureName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lectureStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lectureEndTime;

    public ServerIPListResponse(Server server) {
        this.id = server.getId();
        this.ipId = server.getIp().getId();
        this.ipAddress = server.getIp().getAddress();
        this.ipName = server.getIp().getName();
        this.lectureId = server.getLecture().getId();
        this.lectureName = server.getLecture().getName();
        this.lectureStartTime = server.getLecture().getStartTime();
        this.lectureEndTime = server.getLecture().getEndTime();
    }
}
