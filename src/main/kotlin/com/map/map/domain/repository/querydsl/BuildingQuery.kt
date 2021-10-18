package com.map.map.domain.repository.querydsl

import com.map.map.domain.dto.building.SearchBuildingDto
import com.map.map.domain.entity.*
import com.map.map.domain.entity.QAlbum.album
import com.map.map.domain.entity.QBuilding.building
import com.map.map.domain.entity.QUser.user
import com.map.map.domain.entity.QVisited.visited
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
                lessThanAlbumId(lastAlbumId)
            )
            .orderBy(album.idx.desc())
            .limit(8)
            .fetch()
    }

    fun lessThanAlbumId(lastAlbumId: Long?): BooleanExpression?{
        return if(lastAlbumId != null){
            album.idx.lt(lastAlbumId)
        }else{
            null
        }
    }

    fun getUsers(buildingAddress : String, lastUserId: Long?): List<User> {
        return queryFactory
            .select(user)
            .from(building)
            .join(building.visitor, visited)
            .join(visited.user, user)
            .where(
                building.address.eq(buildingAddress),
                lessThanUserId(lastUserId)
            )
            .orderBy(user.idx.desc())
            .limit(8)
            .fetch()
    }

    fun lessThanUserId(lastUserId: Long?): BooleanExpression?{
        return if(lastUserId != null){
            user.idx.lt(lastUserId)
        }else{
            null
        }
    }

    fun searchBuilding(searchBuildingDto: SearchBuildingDto) : List<Building>{
        return queryFactory
            .select(building)
            .from(building)
            .where(building.address.like(searchBuildingDto.value),
                lessThanBuildingId(searchBuildingDto.lastBuildingId))
            .orderBy(building.id.desc())
            .limit(10)
            .fetch()
    }
    fun lessThanBuildingId(lastBuildingId: Long?): BooleanExpression?{
        return if(lastBuildingId != null){
            building.id.lt(lastBuildingId)
        }else{
            null
        }
    }
}