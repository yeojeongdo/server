package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.entity.Comment
import com.map.map.domain.entity.User
import com.map.map.domain.response.comment.CommentRo
import com.map.map.domain.response.user.UserInfoRo
import com.map.map.service.user.userToUserInfoRo

fun postCommentDtoAndUserToComment(postCommentDto: PostCommentDto, user: User, comment: Comment){
    comment.content = postCommentDto.comment
    comment.user = user

}

fun commentToCommentRo(comment: Comment, commentRo: CommentRo, serverAddress: String){
    commentRo.id = comment.idx
    commentRo.content = comment.content

    commentRo.user = UserInfoRo()
    userToUserInfoRo(comment.user!!, commentRo.user!!, serverAddress)
}