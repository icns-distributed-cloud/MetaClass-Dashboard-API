package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;
import com.classMetabus.web.Admin.dto.department.CreateDepartmentRequest;
import com.classMetabus.web.Admin.dto.department.DeleteDepartmentRequest;
import com.classMetabus.web.Admin.service.DepartmentDaoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
@Api(tags = {"부서 생성/삭제/리스트업"})
public class DepartmentController {
    private final DepartmentDaoService departmentDaoService;
    @PostMapping("/post/postdepartment")
    public ResponseEntity create(@RequestBody CreateDepartmentRequest request){
        String message = "부서 등록을 성공했습니다.";
        return new ResponseEntity(CommonResponse.res(departmentDaoService.create(request), StatusCode.OK, message,null),null, HttpStatus.CREATED);
    }
    @GetMapping("get/departmentlist")
    public ResponseEntity departmentList(){
        String message = "부서 리스트를 성공적으로 불러왔습니다.";
        return new ResponseEntity(CommonResponse.res(true,StatusCode.OK, message,departmentDaoService.departmentList()),null, HttpStatus.OK);
    }

    @PatchMapping("patch/deletedepartment")
    public ResponseEntity deleteById(@RequestBody DeleteDepartmentRequest request){
        Boolean result = departmentDaoService.deleteById(request);
        String message =  "해당 부서로 등록된 학생이 있거나 이미 삭제된 부서입니다.";
        if (result == true) message =  "부서 삭제를 성공했습니다.";

        return new ResponseEntity(CommonResponse.res(result, StatusCode.OK, message,null),null, HttpStatus.OK);
    }
}
