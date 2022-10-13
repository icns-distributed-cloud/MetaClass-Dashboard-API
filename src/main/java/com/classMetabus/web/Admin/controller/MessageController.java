package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.mail.SendMailRequest;
import com.classMetabus.web.Admin.service.MessageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"학생에게 문자 메시지 전송"})
@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody SendMailRequest request){
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, "메세지를 전송했습니다.",messageService.sendMessage("관리자", request.getContext())),null, HttpStatus.OK);
    }

    /*
    //미사용
    @GetMapping("/message/log")
    public ResponseEntity findLog(){
        return new ResponseEntity(CommonResponse.res(StatusCode.OK, messageService.findAll()),null, HttpStatus.OK);
    }*/
}
