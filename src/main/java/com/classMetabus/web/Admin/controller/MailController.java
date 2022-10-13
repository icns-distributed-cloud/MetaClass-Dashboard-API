package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.mail.SendMailRequest;
import com.classMetabus.web.Admin.service.MailService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"학생에게 이메일 전송"})
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity sendMail(@RequestBody SendMailRequest request){
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, "메일전송을 성공했습니다.",mailService.sendMail(request.getInstructorId(), request.getContext())), null, HttpStatus.OK);
    }
    /*
    // 미사용
    @GetMapping("/log")
    public ResponseEntity findLog(final Pageable pageable){
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK,"메일찾기를 성공했습니다.", mailService.findAll(pageable)),null, HttpStatus.OK);
    }*/
}
