package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.follow.FollowNumbersDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.service.follow.FollowService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("follow")
class FollowController @Autowired constructor(
    private var followService: FollowService
) {
    @AutoLogging
    @PatchMapping("/{userIdx}")
    @ApiOperation("팔로우하기")
    fun following(@PathVariable @Valid @NotNull userIdx:Long, request: HttpServletRequest): Response {
        var userId = request.getAttribute("userId") as String
        followService.changeFollowState(userId, userIdx)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @GetMapping("/numbers/{userIdx}")
    @ApiOperation("팔로워, 팔로잉 개수")
    fun getFollowNumber(@PathVariable @Valid @NotNull userIdx: Long, request: HttpServletRequest): ResponseData<FollowNumbersDto> {
        var userId = request.getAttribute("userId") as String
        val followNumbersDto = followService.getFollowNum(userIdx)
        return ResponseData(HttpStatus.OK, "성공", followNumbersDto)
    }

    @AutoLogging
    @GetMapping("/state/{userIdx}")
    @ApiOperation("팔로우 상태")
    fun getFollowState(@PathVariable @Valid @NotNull userIdx: Long, request: HttpServletRequest): ResponseData<Boolean> {
        var userId = request.getAttribute("userId") as String
        val followState = followService.getFollowState(userIdx, userId)
        return ResponseData(HttpStatus.OK, "성공", followState)
    }
}