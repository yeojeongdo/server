package com.map.map.domain.repository

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.AlbumLike
import com.map.map.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface LikeRepo : JpaRepository<AlbumLike, Long>{
    @Query(value = "select count(*) from album_like where User_idx = :id", nativeQuery = true)
    fun countAlbumLikeNum(@Param("id") id: Long) : Long
    fun findAlbumLikeByAlbumAndUser(album: Album, user: User): Optional<AlbumLike>
}