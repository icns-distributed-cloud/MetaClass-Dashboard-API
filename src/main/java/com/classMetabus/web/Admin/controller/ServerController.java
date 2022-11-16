package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.server.CreateServerIPRequest;
import com.classMetabus.web.Admin.dto.server.DeleteServerIPRequest;
import com.classMetabus.web.Admin.dto.server.ServerIPListRequest;
import com.classMetabus.web.Admin.service.ServerDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
@AllArgsConstructor
@Api(tags = {"서버 등록/삭제/조회"})
public class ServerController {
    private final ServerDaoService serverDaoService;

    @PostMapping("/post/createserver")
    public ResponseEntity create(@RequestBody CreateServerIPRequest request){
        Boolean result = serverDaoService.create(request);
        String message = "서버 내 강의실 등록을 실패했습니다.";
        if (result == true) message =  "서버 내 강의실 등록을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }

    @PostMapping("/post/listserver")
    public ResponseEntity serverList(@RequestBody ServerIPListRequest request) {
        String message = "서버 리스트를 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,serverDaoService.ServerIPList(request)),null, HttpStatus.OK);
    }

    @DeleteMapping("/delete/deleteserver")
    public ResponseEntity deleteById(@RequestBody DeleteServerIPRequest request){
        Boolean result = serverDaoService.deleteById(request);
        String message =  "존재하지 않는 서버Id입니다.";
        if (result == true) message =  "삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @GetMapping("/get/oneserver")
    public ResponseEntity getServerByLectureId (@RequestParam Integer lectureId){
        String message = "서버 정보를 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,serverDaoService.getServerIPByLectureId(lectureId)),null, HttpStatus.OK);
    }

    @GetMapping("/get/findlectureinfo")
    public ResponseEntity findLectureInfo (@RequestParam Integer instructorId){
        String message = "강의자의 강좌 리스트를 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,serverDaoService.findLectureInfo(instructorId)),null, HttpStatus.OK);
    }
}
