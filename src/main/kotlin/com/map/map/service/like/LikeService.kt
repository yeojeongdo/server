package com.map.map.service.like

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.User
import com.map.map.domain.response.like.LikedUsersRo
import org.springframework.transaction.annotation.Transactional

interface LikeService {
    fun findUserById(userId: String): User
    fun findAlbum(id: Long): Album
    fun changeLikeAlbumState(userId: String, albumId: Long)
    @Transactional(readOnly = true)
    fun isLike(userId: String, albumId: Long): Boolean
    @Transactional(readOnly = true)
    fun getLikedUsers(albumId: Long): List<LikedUsersRo>
}