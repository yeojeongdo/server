package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.user.UserInfoRo
import com.map.map.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("user")
class UserController @Autowired constructor(
    private var userService: UserService
){
    @AutoLogging
    @PatchMapping("name")
    @ApiOperation("이름 변경")
    fun updateUserName(@RequestBody @Valid patchUserNameDto: PatchUserNameDto, request: HttpServletRequest): Response {
        val userId = request.getAttribute("userId") as String
        userService.changeUserName(patchUserNameDto, userId)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @PatchMapping("birth-date")
    @ApiOperation("생일 변경")
    fun updateUserBirthDate(@RequestBody @Valid patchUserBirthDateDto: PatchUserBirthDateDto, request: HttpServletRequest) : Response{
        val userId = request.getAttribute("userId") as String
        userService.changeUserBirthDate(patchUserBirthDateDto, userId)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @DeleteMapping
    @ApiOperation("유저 삭제")
    fun deleteUser(@RequestBody @Valid deleteUserDto: DeleteUserDto, request: HttpServletRequest) : Response{
        val userId = request.getAttribute("userId") as String
        userService.deleteUser(deleteUserDto, userId)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @GetMapping
    @ApiOperation("유저 정보 확인")
    fun getUserInfo(request: HttpServletRequest): ResponseData<UserInfoRo> {
        val userId = request.getAttribute("userId") as String
        val data = userService.getUserInfo(userId)
        return ResponseData(HttpStatus.OK, "성공", data)
    }

    @AutoLogging
    @PatchMapping("image")
    @ApiOperation("프로필 사진 변경")
    fun patchUserImage(@RequestParam file : MultipartFile, request : HttpServletRequest): Response {
        val userId = request.getAttribute("userId") as String
        userService.changeUserImage(file, userId)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @GetMapping("/followers/{userIdx}")
    @ApiOperation("팔로워 받아오기")
    fun getFollowerList(@PathVariable @Valid @NotNull userIdx: Long, @RequestParam lastId: Long, request: HttpServletRequest): ResponseData<List<UserInfoRo>>{
        val userId = request.getAttribute("userId") as String
        val userList = userService.getFollowers(userIdx, lastId)
        return ResponseData(HttpStatus.OK,"성공", userList)
    }
}