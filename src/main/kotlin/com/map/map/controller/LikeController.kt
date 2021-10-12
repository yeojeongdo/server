package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.like.LikeDto
import com.map.map.domain.response.Response
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
}