package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.entity.Comment
import com.map.map.domain.entity.User

fun postCommentDtoAndUserToComment(postCommentDto: PostCommentDto, user: User, comment: Comment){
    comment.content = postCommentDto.comment
    comment.user = user

}