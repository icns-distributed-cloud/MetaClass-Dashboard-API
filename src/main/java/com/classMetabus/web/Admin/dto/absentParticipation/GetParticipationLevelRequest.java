package com.classMetabus.web.Admin.dto.absentParticipation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetParticipationLevelRequest {
    public Integer studentListId;
    public Integer participationLevel;
}
