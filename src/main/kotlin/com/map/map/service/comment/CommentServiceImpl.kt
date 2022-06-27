package com.map.map.service.comment

import com.map.map.domain.dto.comment.PostCommentDto
import com.map.map.domain.entity.Comment
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.CommentRepo
import com.map.map.domain.repository.querydsl.CommentQuery
import com.map.map.domain.response.comment.CommentRo
import com.map.map.exception.CustomHttpException
import com.map.map.service.album.AlbumService
import com.map.map.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.streams.toList

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
        comment.album = album
        commentRepo.save(comment)
        albumRepo.save(album)
    }

    @Transactional(readOnly = true)
    override fun getCommentList(albumId: Long, lastCommentId: Long?, serverAddress: String) : List<CommentRo>{
        albumService.findAlbum(albumId)
        val comments = commentQuery.getCommentList(albumId, lastCommentId)

        return comments.stream().map {
            val commentRo = CommentRo()
            commentToCommentRo(it, commentRo, serverAddress)
            commentRo
        }.toList()
    }

    @Transactional(readOnly = true)
    override fun getCommentAllList(albumId: Long, serverAddress: String): List<CommentRo> {
        val comments = commentRepo.findAllByAlbumIdx(albumId)

        return comments.stream().map {
            val commentRo = CommentRo()
            commentToCommentRo(it, commentRo, serverAddress)
            commentRo
        }.toList()
    }

    @Transactional
    override fun deleteComment(commentId: Long, userId: String) {
        val user = userService.getUser(userId)

        commentRepo.deleteByIdxAndUser(commentId, user)
    }

    @Transactional
    override fun patchComment(commentDto: PostCommentDto, userId: String) {
        val user = userService.getUser(userId)

        val comment = commentRepo.findByIdxAndUser(commentDto.id!!, user)
            ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.")

        comment.content = commentDto.comment

        commentRepo.save(comment)
    }
}