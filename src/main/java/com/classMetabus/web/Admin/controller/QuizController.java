package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.quiz.CreateQuizRequest;
import com.classMetabus.web.Admin.dto.quiz.ScoringRequest;
import com.classMetabus.web.Admin.dto.quiz.ScoringResponse;
import com.classMetabus.web.Admin.dto.quiz.UpdateQuizRequest;
import com.classMetabus.web.Admin.service.QuizDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@AllArgsConstructor
@Api(tags = {"퀴즈 등록, 삭제, 확인, 성적처리"})
public class QuizController {
    private QuizDaoService quizDaoService;

    @PostMapping("/post/createquiz")
    public ResponseEntity create(@RequestBody CreateQuizRequest request) {
        String message = "퀴즈 등록을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(quizDaoService.create(request), StatusCode.OK, message, null), null, HttpStatus.CREATED);
    }

    @GetMapping("/get/deletequiz")
    public ResponseEntity deleteById(@RequestParam Integer id) {
        Boolean result = quizDaoService.deleteById(id);
        String message = "강의에 이미 등록된 퀴즈입니다.";
        if (result == true) {
            message = "퀴즈 삭제를 성공했습니다.";
        }
        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message, null), null, HttpStatus.OK);
    }

    @GetMapping("get/list")
    public ResponseEntity listByInstructorId(@RequestParam Integer instructorId) {
        String message = "리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message, quizDaoService.listByInstructorId(instructorId)), null, HttpStatus.OK);
    }

    @GetMapping("get/listbyquizid")
    public ResponseEntity listByQuizId(@RequestParam Integer quizId) {
        String message = "리스트를 성공적으로 가져왔습니다.";
        return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message, quizDaoService.listByQuizId(quizId)), null, HttpStatus.OK);
    }

    @PostMapping("/post/updatequiz")
    public ResponseEntity updateById(@RequestBody UpdateQuizRequest request){
        Boolean result = quizDaoService.updateById(request);
        String message = "존재하지 않는 퀴즈 정보입니다.";
        if (result == true) {
            message = "퀴즈 수정을 성공했습니다.";
        }
        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message, null), null, HttpStatus.OK);
    }

    @PostMapping("/post/scoring")
    public ResponseEntity scoring(@RequestBody ScoringRequest request){
        ScoringResponse result = quizDaoService.scoring(request);
        String message = "존재하지 않는 퀴즈 정보입니다.";
        if (result.getSuccess() == true) {
            message = "퀴즈 채점을 성공했습니다.";
        }
        return new ResponseEntity(CommonResponse.res(result.getSuccess(), StatusCode.OK, message, result), null, HttpStatus.OK);
    }
}
