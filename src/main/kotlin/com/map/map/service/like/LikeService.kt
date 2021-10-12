package com.map.map.service.like

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.User

interface LikeService {
    fun findUserById(userId: String): User
    fun findAlbum(id: Long): Album
    fun changeLikeAlbumState(userId: String, albumId: Long)
}