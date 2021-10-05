package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto

interface CommentService {
    fun makeComment(postCommentDto: PostCommentDto, userId:String)
}