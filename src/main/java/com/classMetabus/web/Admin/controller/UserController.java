package com.classMetabus.web.Admin.controller;
import com.classMetabus.web.Admin.dto.common.ApiResponse;
import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.user.*;
import com.classMetabus.web.Admin.service.UserDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Api(tags = {"회원가입, 로그인, 회원 정보 리스트, 수정, 탈퇴"})
public class UserController {
    private UserDaoService userDaoService;
    @PostMapping("/post/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        String message = "회원가입을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(userDaoService.register(request), StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }

    @PostMapping("/post/login")
    public ApiResponse login(@RequestBody LoginRequest request){
        LoginResponse response = userDaoService.findOne(request);
        ApiResponse apiResponse;
        if (response.getId() != null){
            apiResponse = ApiResponse.builder()
                    .success(true)
                    .code(200)
                    .message("로그인을 성공했습니다.")
                    .data(response)
                    .build();
        }
        else {
            apiResponse = ApiResponse.builder()
                    .success(false)
                    .code(200)
                    .message("로그인을 실패했습니다.")
                    .build();
        }
        return apiResponse;
    }

    @GetMapping("get/allstudent")
    public ResponseEntity allStudentList(){
        String message = "학생 회원정보 조회를 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(true, StatusCode.OK, message,userDaoService.findStudentList()),null, HttpStatus.OK);
    }

    @GetMapping("get/allInstructor")
    public ResponseEntity allLectureList(){
        String message = "강사 회원정보 조회를 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,userDaoService.findLectureList()),null, HttpStatus.OK);
    }

    @PostMapping("post/studentlistbydepartment")
    public ResponseEntity studentListByDepartment(@RequestBody StudentListByDepartmentRequest request){
        String message = "부서별 학생 리스트 조회를 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,userDaoService.studentListByDepartment(request)),null, HttpStatus.OK);
    }

    @PatchMapping("patch/updateuser")
    public ResponseEntity updateById(@RequestBody UpdateRequest request){
        Boolean result = userDaoService.updateById(request);
        String message =  "없는 계정이거나 이미 삭제된 계정입니다.";
        if (result == true) message =  "회원수정을 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PatchMapping("patch/deleteuser")
    public ResponseEntity deleteById(@RequestBody DeleteRequest request) {
        Boolean result = userDaoService.deleteById(request);
        String message =  "없는 계정이거나 이미 삭제된 계정입니다.";
        if (result == true) message =  "회원탈퇴를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("post/checkLoginId")
    public ResponseEntity checkLoginId(@RequestBody checkLoginIdRequest request){
        Boolean result = userDaoService.checkLoginId(request);
        String message =  "존재하는 아이디입니다.";
        if (result == true) message =  "사용할 수 있는 아이디입니다.";
        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }

    @PostMapping("post/changePassword")
    public ResponseEntity changePassword (@RequestBody ChangePasswordRequest request){
        Boolean result = userDaoService.changePassword(request);
        String message =  "존재하지 않은 아이디입니다.";
        if(request.getPassword().trim().isEmpty()){message =  "입력되지않은 비밀번호입니다.";}
        if (result == true) message = "비밀번호 변경을 성공했습니다.";
        return  new ResponseEntity(CommonResponse.res(result,StatusCode.OK,message,null),null,HttpStatus.OK);
    }
}
