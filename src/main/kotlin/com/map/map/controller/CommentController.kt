package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.comment.CommentRo
import com.map.map.service.comment.CommentService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/comment")
class CommentController @Autowired constructor(
    private var commentService: CommentService
){
    @AutoLogging
    @PostMapping
    @ApiOperation("댓글 작성")
    fun writeComment(@RequestBody @Valid postCommentDto: PostCommentDto, request: HttpServletRequest) : Response{
        val userId = request.getAttribute("userId") as String
        commentService.makeComment(postCommentDto, userId)
        return Response(HttpStatus.OK, "성공")
    }

    @AutoLogging
    @GetMapping("/list/{albumId}")
    @ApiOperation("댓글리스트 받아오기")
    fun getCommentList(@PathVariable albumId:Long, @RequestParam lastCommentId:Long?): ResponseData<MutableList<CommentRo>>{
        val data = commentService.getCommentList(albumId, lastCommentId)
        return ResponseData(HttpStatus.OK, "성공", data)
    }
}