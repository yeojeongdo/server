package com.map.map.domain.repository.querydsl

import com.map.map.domain.entity.QAlbum
import com.map.map.domain.entity.QAlbum.album
import com.map.map.domain.entity.QAlbumLike
import com.map.map.domain.entity.QAlbumLike.albumLike
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class LikeQuery @Autowired constructor(
    private var em : EntityManager,
    private var queryFactory: JPAQueryFactory
){
    fun getLikeNum(albumId : Long): Long{
        return queryFactory
            .select(albumLike)
            .from(album)
            .join(album.likes, albumLike)
            .where(album.idx.eq(albumId).and(albumLike.isState.eq(true)))
            .fetchCount();
    }
}