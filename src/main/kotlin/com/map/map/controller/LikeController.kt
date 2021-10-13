package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.like.LikeDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.like.LikedUsersRo
import com.map.map.service.like.LikeService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/like")
class LikeController @Autowired constructor(
    private val likeService: LikeService,
) {

    @AutoLogging
    @PatchMapping
    @ApiOperation("좋아요 상태 변경")
    fun patchLikeState(@RequestBody likeDto: LikeDto, request: HttpServletRequest): Response {
        val userId = request.getAttribute("userId") as String
        likeService.changeLikeAlbumState(userId, likeDto.albumId!!)

        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @GetMapping
    @ApiOperation("좋아요 상태 확인")
    fun getLikeState(@RequestParam albumId: Long, request: HttpServletRequest): ResponseData<Boolean> {
        val userId = request.getAttribute("userId") as String
        val data = likeService.isLike(userId, albumId)

        return ResponseData(HttpStatus.OK, "성공", data)
    }

    @AutoLogging
    @GetMapping("/users")
    @ApiOperation("좋아요 누른 유저들")
    fun getUsersLiked(@RequestParam albumId: Long): ResponseData<MutableList<LikedUsersRo>> {
        val data = likeService.getLikedUsers(albumId)

        return ResponseData(HttpStatus.OK, "성공", data)
    }
}