package com.map.map.domain.repository.querydsl

import com.map.map.domain.entity.QAlbum
import com.map.map.domain.entity.QAlbum.album
import com.map.map.domain.entity.QComment
import com.map.map.domain.entity.QComment.*
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CommentQuery @Autowired constructor(
    private var em: EntityManager,
    private var queryFactory: JPAQueryFactory
){
    fun getCommentNum(albumId:Long): Long{
        return queryFactory
            .select(comment)
            .from(album)
            .join(album.comments, comment)
            .where(album.idx.eq(albumId))
            .fetchCount()

    }
}