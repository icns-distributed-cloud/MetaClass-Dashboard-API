package com.classMetabus.web.Admin.controller;

import com.classMetabus.web.Admin.dto.auth.JwtResponse;
import com.classMetabus.web.Admin.dto.auth.LoginRequest;
import com.classMetabus.web.Admin.dto.common.CommonResponse;
import com.classMetabus.web.Admin.dto.common.StatusCode;

import com.classMetabus.web.Admin.service.AuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Api(tags = {"유저인증"})
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity logIn(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse response = authService.login(loginRequest);
        return new ResponseEntity(CommonResponse.res(StatusCode.OK,response), null, HttpStatus.OK);
    }
}
