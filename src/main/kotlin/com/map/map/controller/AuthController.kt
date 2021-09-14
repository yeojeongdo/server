package com.map.map.controller

import com.map.map.domain.dto.auth.CheckIdDto
import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.response.Response
import com.map.map.service.auth.AuthServiceImpl
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController @Autowired constructor(
    val authService: AuthServiceImpl,
) {
    @PostMapping("/register")
    @ApiOperation("회원가입")
    fun register(@RequestBody @Valid registerDto: RegisterDto): Response {
        authService.register(registerDto)
        return Response(HttpStatus.CREATED, "성공")
    }

    @GetMapping("/check-id")
    @ApiOperation("아이디 존재 여부")
    fun checkId(@RequestParam @Valid checkIdDto: CheckIdDto): Response {
        authService.checkId(checkIdDto)
        return Response(HttpStatus.OK, "성공")
    }

}