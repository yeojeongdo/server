package com.map.map.controller

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.response.Response
import com.map.map.lib.Crypto
import com.map.map.service.AuthService
import com.map.map.service.AuthServiceImpl
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController @Autowired constructor (
    val authService: AuthServiceImpl,
) {
    @PostMapping("/register")
    @ApiOperation("회원가입")
    fun register(@RequestBody @Valid  registerDto: RegisterDto): Response {
        authService.register(registerDto)
        return Response(HttpStatus.OK, "성공")
    }
}