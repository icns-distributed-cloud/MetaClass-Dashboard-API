package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.absentParticipation.GetAbsentLectureRequest;
import com.classMetabus.web.Admin.dto.absentParticipation.StudentLectureRequest;
import com.classMetabus.web.Admin.dto.absentParticipation.GetParticipationLevelRequest;
import com.classMetabus.web.Admin.service.ParticipationDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participation")
@AllArgsConstructor
@Api(tags = {"수업 참여도, 출결"})
public class AbsentParticipationController {
    private final ParticipationDaoService participationDaoService;
    @PostMapping("/post/joinLecture")
    public ResponseEntity joinLecture(@RequestBody StudentLectureRequest request) {
        String message = "학생 리스트 아이디를 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message,participationDaoService.findByStudentListId(request)),null, HttpStatus.OK);
    }

    @PostMapping("/post/getabsentlecture")
    public ResponseEntity getAbsentLecture(@RequestBody GetAbsentLectureRequest request) {
        String message = "학생 출결 등록을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,participationDaoService.getAbsentLecture(request)),null, HttpStatus.OK);

    }
    @PostMapping("/post/participationlevel")
    public ResponseEntity update(@RequestBody GetParticipationLevelRequest request) {
        Boolean result = participationDaoService.updateParticipationLevel(request);
        String message =  "학생 참여도 등록을 실패했습니다.";
        if (result == true) {
            message =  "학생 참여도 등록을 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
        }
        else return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }
}