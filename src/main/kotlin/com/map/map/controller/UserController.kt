package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.response.Response
import com.map.map.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("user")
class UserController @Autowired constructor(
    private var userService: UserService
){
    @AutoLogging
    @PatchMapping("name")
    @ApiOperation("이름 변경")
    fun updateUserName(@RequestBody @Valid patchUserNameDto: PatchUserNameDto, request: HttpServletRequest): Response {
        val user = request.getAttribute("user") as User
        userService.changeUserName(patchUserNameDto, user)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @PatchMapping("birth-date")
    @ApiOperation("생일 변경")
    fun updateUserBirthDate(@RequestBody @Valid patchUserBirthDateDto: PatchUserBirthDateDto, request: HttpServletRequest) : Response{
        val user = request.getAttribute("user") as User
        userService.changeUserBirthDate(patchUserBirthDateDto, user)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @DeleteMapping
    @ApiOperation("유저 삭제")
    fun deleteUser(@RequestBody @Valid deleteUserDto: DeleteUserDto, request: HttpServletRequest) : Response{
        val user = request.getAttribute("user") as User
        userService.deleteUser(deleteUserDto, user)
        return Response(HttpStatus.OK, "성공")
    }
}