package com.map.map.controller

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.response.Response
import com.map.map.service.auth.AuthServiceImpl
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
) {
    @PostMapping("/register")
    @ApiOperation("회원가입")
    fun register(@RequestBody @Valid registerDto: RegisterDto): Response {
        authService.register(registerDto)
        return Response(HttpStatus.CREATED, "성공")
    }

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

}