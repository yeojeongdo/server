package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.response.comment.CommentRo
import org.springframework.transaction.annotation.Transactional

interface CommentService {
    fun makeComment(postCommentDto: PostCommentDto, userId:String)
    fun getCommentList(albumId:Long, lastCommentId:Long?, serverAddress: String) : List<CommentRo>
    fun deleteComment(commentId:Long, userId:String)
    fun patchComment(commentDto: PostCommentDto, userId: String)
    @Transactional(readOnly = true)
    fun getCommentAllList(albumId: Long, serverAddress: String): List<CommentRo>
}