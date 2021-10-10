package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.response.comment.CommentRo

interface CommentService {
    fun makeComment(postCommentDto: PostCommentDto, userId:String)
    fun getCommentList(albumId:Long, lastCommentId:Long?) : MutableList<CommentRo>
    fun deleteComment(commentId:Long, userId:String)
    fun patchComment(commentDto: PostCommentDto, userId: String)
}