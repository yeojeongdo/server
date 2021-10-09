package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.entity.Comment
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.CommentRepo
import com.map.map.domain.repository.querydsl.CommentQuery
import com.map.map.domain.response.comment.CommentRo
import com.map.map.service.album.AlbumService
import com.map.map.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl @Autowired constructor(
    private var commentRepo: CommentRepo,
    private var userService: UserService,
    private var albumService: AlbumService,
    private var albumRepo: AlbumRepo,
    private var commentQuery: CommentQuery,
):CommentService{

    @Transactional
    override fun makeComment(postCommentDto: PostCommentDto, userId:String) {
        val user = userService.getUser(userId)

        val album = albumService.findAlbum(postCommentDto.id!!)

        val comment = Comment()
        postCommentDtoAndUserToComment(postCommentDto, user, comment)

        album.comments.add(comment)

        commentRepo.save(comment)
        albumRepo.save(album)
    }

    @Transactional(readOnly = true)
    override fun getCommentList(albumId: Long, lastCommentId: Long?) : MutableList<CommentRo>{
        albumService.findAlbum(albumId)
        val comments = commentQuery.getCommentList(albumId, lastCommentId)


        var commentRoList = mutableListOf<CommentRo>()
        for (comment:Comment in comments){
            val commentRo = CommentRo()
            commentToCommentRo(comment, commentRo)
            commentRoList.add(commentRo)
        }

        return commentRoList
    }
}