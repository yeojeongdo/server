package com.map.map.domain.repository.querydsl

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.QAlbum
import com.map.map.domain.entity.QAlbum.album
import com.map.map.domain.entity.QBuilding
import com.map.map.domain.entity.QBuilding.building
import com.map.map.domain.entity.QComment
import com.querydsl.core.QueryFactory
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class BuildingQuery @Autowired constructor(
    private var em: EntityManager,
    private var queryFactory: JPAQueryFactory
) {
    fun getAlbums(buildingAddress : String, lastAlbumId : Long?): List<Album>{
        return queryFactory
            .select(album)
            .from(building)
            .join(building.albums, album)
            .where(
                building.address.eq(buildingAddress),
                lessThanId(lastAlbumId)
            )
            .orderBy(album.idx.desc())
            .limit(8)
            .fetch()
    }

    fun lessThanId(lastAlbumId: Long?): BooleanExpression?{
        return if(lastAlbumId != null){
            album.idx.lt(lastAlbumId)
        }else{
            null
        }
    }
}