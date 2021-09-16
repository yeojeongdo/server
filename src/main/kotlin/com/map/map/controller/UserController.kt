package com.map.map.controller

import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.response.Response
import com.map.map.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("user")
class UserController @Autowired constructor(
    private var userService: UserService
){
    @PatchMapping("name")
    fun updateUserName(@RequestBody @Valid patchUserNameDto: PatchUserNameDto, request: HttpServletRequest): Response {
        val user = request.getAttribute("user") as User
        userService.changeUserName(patchUserNameDto, user)
        return Response(HttpStatus.OK, "성공")
    }
}