package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.auth.LoginDto
import com.map.map.domain.dto.auth.RefreshTokenDto
import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.auth.LoginRo
import com.map.map.service.auth.AuthServiceImpl
import com.map.map.service.jwt.JwtService
import io.swagger.annotations.ApiOperation
import org.hibernate.validator.constraints.Length
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("auth")
class AuthController @Autowired constructor(
    val authService: AuthServiceImpl,
    val jwtService: JwtService
) {
    @AutoLogging
    @PostMapping("/register")
    @ApiOperation("회원가입")
    fun register(@RequestBody @Valid
                 registerDto: RegisterDto
    ): Response {
        authService.register(registerDto)
        return Response(HttpStatus.CREATED, "성공")
    }

    @AutoLogging
    @GetMapping("/check-id")
    @ApiOperation("아이디 존재 여부")
    fun checkId( @RequestParam @Valid
        @NotNull(message = "id null 불가능")
        @Length(min = 4, max = 20, message = "id 길이 4~20")
        id: String
    ): Response {
        authService.checkId(id)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @PostMapping("/login")
    @ApiOperation("로그인")
    fun login(@RequestBody @Valid
              loginDto: LoginDto
    ): ResponseData<LoginRo>{
        val loginRo = authService.login(loginDto)
        return ResponseData<LoginRo>(HttpStatus.OK, "성공", loginRo)
    }

    @AutoLogging
    @PostMapping("/token-renewal")
    @ApiOperation("토큰 갱신")
    fun tokenRenewal(@RequestBody @Valid refreshTokenDto: RefreshTokenDto): ResponseData<LoginRo> {
        val data: LoginRo = jwtService.refreshToken(refreshTokenDto.refreshToken!!)

        return ResponseData(HttpStatus.OK, "토큰 갱신 성공", data)
    }

}