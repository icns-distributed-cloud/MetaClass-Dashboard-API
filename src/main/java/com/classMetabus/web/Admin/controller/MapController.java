package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.map.CreateMapRequest;
import com.classMetabus.web.Admin.dto.map.DeleteMapRequest;
import com.classMetabus.web.Admin.dto.map.MapListRequest;
import com.classMetabus.web.Admin.dto.map.UpdateMapRequest;
import com.classMetabus.web.Admin.service.MapDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map")
@AllArgsConstructor
@Api(tags = {"강의실 공간 맵 생성/삭제/리스트업"})
public class MapController {
    private MapDaoService mapDaoService;

    @PostMapping("/post/createmap")
    public  ResponseEntity create(@RequestBody CreateMapRequest request){
        String message = "강의실 맵 생성을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(mapDaoService.create(request), StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }

    @PatchMapping("/patch/deletemap")
    public ResponseEntity deleteById(@RequestBody DeleteMapRequest request){
        Boolean result = mapDaoService.deleteById(request);
        String message =  "현재 사용중인 강의실이거나 이미 삭제된 강의실입니다.";
        if (result == true) message =  "강의실 삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("/post/maplist")
    public ResponseEntity mapList(@RequestBody MapListRequest request) {
        String message = "강의실 맵 리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,mapDaoService.ListByInstructor(request)),null, HttpStatus.OK);
    }

    @PatchMapping("/patch/updatemap")
    public ResponseEntity updateById(@RequestBody UpdateMapRequest request){
        Boolean result = mapDaoService.updateById(request);
        String message =  "이미 등록된 이름이거나 존재 하지 않는 강의실입니다.";
        if (result == true) message =  "강의실 수정을 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }
}
