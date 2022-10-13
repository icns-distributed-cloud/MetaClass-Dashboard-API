package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.ip.CreateIpRequest;
import com.classMetabus.web.Admin.service.IpDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ip")
@AllArgsConstructor
@Api(tags = {"서버 아이피 등록/삭제/조회"})
public class IpController {
    private final IpDaoService ipDaoService;

    @PostMapping("/post/create")
    public ResponseEntity create(@RequestBody CreateIpRequest request){
        String message = "서버 내 강의실 등록을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(ipDaoService.create(request), StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }
    @GetMapping("/get/delete")
    public ResponseEntity deleteById(@RequestParam Integer id){
        Boolean result = ipDaoService.deleteById(id);
        String message =  "현재 사용중인 아이피입니다.";
        if (result == true) message =  "삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }
    @GetMapping("/get/list")
    public ResponseEntity list(){
        String message = "아이피 리스트를 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,ipDaoService.list()),null, HttpStatus.OK);
    }
}
