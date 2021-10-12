package com.map.map.domain.repository

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepo : JpaRepository<Album, Long> {
    fun findTop10ByOrderByIdxDesc(): MutableList<Album>
    fun findTop10ByUserOrderByIdxDesc(user: User): MutableList<Album>
    fun findTop10ByIdxLessThanOrderByIdxDesc(id:Long): MutableList<Album>
    fun findTop10ByUserAndIdxLessThanOrderByIdxDesc(user: User, id:Long): MutableList<Album>
    fun findByIdx(idx: Long): Album?
}