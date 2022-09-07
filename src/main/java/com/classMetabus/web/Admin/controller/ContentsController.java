package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.content.ContentListRequest;
import com.classMetabus.web.Admin.dto.content.DeleteContentRequest;
import com.classMetabus.web.Admin.dto.content.GetContentInfoByIdRequest;
import com.classMetabus.web.Admin.dto.content.UpdateIdByContentIdRequest;
import com.classMetabus.web.Admin.dto.content.ContentListResponse;
import com.classMetabus.web.Admin.dto.content.CreateContentResponse;
import com.classMetabus.web.Admin.service.ContentsDaoService;
import com.classMetabus.web.Admin.service.FileService;
import io.swagger.annotations.Api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/content")
@AllArgsConstructor
@Api(tags = {"콘텐츠 생성/삭제/리스트"})
public class ContentsController {
    private final ContentsDaoService contentsDaoService;
    @Autowired
    private final FileService fileService;
    @PostMapping("/post/createcontent")
    public ResponseEntity create(@RequestBody MultipartFile file){
        String directory = fileService.fileUpload(file);
        CreateContentResponse result = contentsDaoService.create(file,directory);

        if(result.getContentId() != null){
            String message = "콘텐츠 생성을 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message,result),null, HttpStatus.CREATED);
        }
        else {
            String message = "콘텐츠 생성을 실패했습니다.";
            return new ResponseEntity(CommonResponse.res(false, StatusCode.OK, message,result),null, HttpStatus.CREATED);
        }
    }

    @PatchMapping("/patch/deletecontent")
    public ResponseEntity deleteById(@RequestBody DeleteContentRequest request){
        Boolean result = contentsDaoService.deleteById(request);
        String message =  "이미 사용중인 콘텐츠입니다.";
        if (result == true) message =  "콘텐츠 삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("/post/contentlist")
    public ResponseEntity contentsList(@RequestBody ContentListRequest request){
        String message = "리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,contentsDaoService.contentList(request)),null, HttpStatus.OK);
    }
    @PostMapping("/post/updateidbycontentid")
    public ResponseEntity updateIdByContentId(@RequestBody UpdateIdByContentIdRequest request){
        Boolean result = contentsDaoService.updateIdByContentId(request);
        String message =  "강사id 업데이트를 실패했습니다.";
        if (result == true) message =  "강사id 업데이트를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);

    }

    //create 호출 후 해당 API 반드시 호출
    @PostMapping("/post/getcontentinfobyid")
    public ResponseEntity getContentInfoById(@RequestBody GetContentInfoByIdRequest request){
        Optional<ContentListResponse> result = contentsDaoService.getContentInfoById(request);
        String message = "존재하지 않는 콘텐츠 아이디입니다.";
        if (result.isPresent()) {
            message =  "강사id 업데이트를 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,result),null, HttpStatus.OK);
        }
        else return new ResponseEntity(CommonResponse.res(false,StatusCode.OK, message,result),null, HttpStatus.OK);
    }
}
