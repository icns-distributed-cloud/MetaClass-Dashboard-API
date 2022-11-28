package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.certificate.*;
import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.service.CertificateDaoService;
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
@RequestMapping("api/certificate")
@AllArgsConstructor
@Api(tags = "수료증 생성/삭제/리스트")
class CertificateController {
    private final CertificateDaoService certificateDaoService;
    @Autowired
    private final FileService fileService;

    @PostMapping("/post/createcertificate")
    public ResponseEntity create(@RequestBody MultipartFile file){
        String directory = fileService.fileUpload(file);
        CreateCertificateResponse result = certificateDaoService.create(file,directory);

        if(result.getCertificateId() != null){
            String message = "수료증 양식 생성을 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message,result),null, HttpStatus.CREATED);
        }
        else {
            String message = "수료증 양식 생성을 실패했습니다.";
            return new ResponseEntity(CommonResponse.res(false, StatusCode.OK, message,result),null, HttpStatus.CREATED);
        }
    }

    @PatchMapping("/patch/deletecertificate")
    public ResponseEntity deleteById(@RequestBody DeleteCertificateRequest request){
        Boolean result = certificateDaoService.deleteById(request);
        String message =  "이미 사용중인 콘텐츠입니다.";
        if (result == true) message =  "콘텐츠 삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("/post/certificatelist")
    public ResponseEntity certificateList(@RequestBody CertificateListRequest request){
        String message = "리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,certificateDaoService.certificateList(request)),null, HttpStatus.OK);
    }
    //create 호출 후 해당 API 반드시 호출
    @PostMapping("/post/getcertificateinfobyid")
    public ResponseEntity getCertificateInfoById(@RequestBody GetCertificateInfoByIdRequest request){
        Optional<CertificateListResponse> result = certificateDaoService.getCertificateInfoById(request);
        String message = "존재하지 않는 콘텐츠 아이디입니다.";
        if (result.isPresent()) {
            message =  "강사id 업데이트를 성공했습니다.";
            return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,result),null, HttpStatus.OK);
        }
        else return new ResponseEntity(CommonResponse.res(false,StatusCode.OK, message,result),null, HttpStatus.OK);
    }
}
