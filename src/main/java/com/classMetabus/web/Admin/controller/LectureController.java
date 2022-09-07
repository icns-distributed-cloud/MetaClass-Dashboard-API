package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.absentParticipation.StudentLectureRequest;
import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.lecture.*;
import com.classMetabus.web.Admin.service.LectureDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecture")
@AllArgsConstructor
@Api(tags = {"선생님,학생 강좌 생성/삭제/리스트업"})
public class LectureController {
    private LectureDaoService lectureDaoService;

    @PostMapping("/instructor/post/createlecture")
    public ResponseEntity CreateLecture(@RequestBody CreateLectureRequest request){
        Boolean result = lectureDaoService.create(request);
        String message = "강좌 생성을 실패했습니다.";
        if(result == true){
            message = "강좌 생성을 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.CREATED);
        }
        else return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }

    @PatchMapping("/instructor/patch/updatelecture")
    public ResponseEntity updateLecture(@RequestBody UpdateLectureRequest request){
        Boolean result = lectureDaoService.updateByclassRoomName(request);
        String message = "중복된 강좌 명이거나 존재 하지 않는 강좌입니다.";
        if(result == true){
            message = "강좌 수정을 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.CREATED);
        }
        else return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }

    @PatchMapping("/instructor/patch/deletelecture")
    public ResponseEntity deleteById(@RequestBody DeleteLectureRequest request){
        Boolean result = lectureDaoService.deleteById(request);
        String message =  "수강신청한 학생이 존재하거나 이미 삭제된 강좌입니다.";
        if (result == true) message =  "강좌 삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("/instructor/post/lecturelist")
    public ResponseEntity LectureList(@RequestBody LectureListRequest request){
        String message = "강좌 리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,lectureDaoService.listByInstructorNDate(request)),null, HttpStatus.OK);
    }

    @PostMapping("/instructor/post/cktstubylecture")
    public ResponseEntity checkStudentByLecture(@RequestBody StudentLectureRequest request){
        String message = "강좌 리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,lectureDaoService.checkStudentByLecture(request)),null, HttpStatus.OK);
    }

    @PostMapping("/student/post/lecturelist")
    public ResponseEntity lectureList(@RequestBody StudentLecturListRequest request){
        String message = "강좌 리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,lectureDaoService.listByStudentNDate(request)),null, HttpStatus.OK);
    }

    @PostMapping("/student/post/registerlecturelist")
    public ResponseEntity totalLectureList(@RequestBody StudentLecturListRequest request){
        String message = "강좌 리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,lectureDaoService.totalLectureList(request)),null, HttpStatus.OK);
    }

    @DeleteMapping("/student/delete/deletelecture")
    public ResponseEntity deleteLecture(@RequestBody StudentLectureRequest request){
        Boolean result = lectureDaoService.deleteLectureByStudentId(request);
        String message =  "이미 수강 취소된 강좌입니다.";
        if (result == true) message =  "강좌 수강을 취소했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("/student/post/joinlecture")
    public ResponseEntity joinLecture(@RequestBody StudentLectureRequest request){
        Boolean result = lectureDaoService.joinLecture(request);
        String message =  "강의 수강을 실패했습니다.";
        if (result == true) message =  "강좌 수강을 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("student/post/ParticipationInfo")
    public ResponseEntity getParticipationInfo(@RequestBody StudentLectureRequest request){
        String message = "학생 수업정보를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,lectureDaoService.getParticipationInfo(request)),null, HttpStatus.OK);
    }
}