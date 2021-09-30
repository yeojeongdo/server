package com.map.map.domain.repository

import com.map.map.domain.entity.Album
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepo : JpaRepository<Album, Long> {
    fun findTop10ByOrderByIdxDesc(): MutableList<Album>
    fun findTop10ByIdxLessThanOrderByIdxDesc(id:Long): MutableList<Album>
    fun findByIdx(idx: Long): Album?
}