package com.map.map.service.like

import com.map.map.domain.entity.Album
import com.map.map.domain.entity.AlbumLike
import com.map.map.domain.entity.User
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.LikeRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.exception.CustomHttpException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LikeServiceImpl @Autowired constructor(
    private val albumRepo: AlbumRepo,
    private val userRepo: UserRepo,
    private val likeRepo: LikeRepo,
): LikeService {


    /**
     * 앨범 좋아요 상태 변경
     */
    @Transactional
    override fun changeLikeAlbumState(userId: String, albumId: Long) {
        val user = findUserById(userId)
        val album = findAlbum(albumId)
        val albumLike = likeRepo.findAlbumLikeByAlbumAndUser(albumId, user.idx!!).orElse(AlbumLike(user, album, false))

        albumLike.isState = !albumLike.isState!!
        album.likes.add(albumLike)

        likeRepo.save(albumLike)
    }

    override fun findUserById(userId: String): User {
        return userRepo.findById(userId) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "없는 사용자")
    }

    override fun findAlbum(id:Long) : Album {
        return albumRepo.findByIdx(id) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "앨범을 찾을 수 없음")

    }
}