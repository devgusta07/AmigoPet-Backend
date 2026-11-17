package com.petshop.petshop_api.controller;

import com.petshop.petshop_api.dto.request.loguin.LoguinRequest;
import com.petshop.petshop_api.dto.response.loguin.LoguinResponse;
import com.petshop.petshop_api.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoguinResponse> login(
            @RequestBody LoguinRequest loguinRequest) {

        return ResponseEntity.ok(loginService.loguin(loguinRequest));
    }
}
