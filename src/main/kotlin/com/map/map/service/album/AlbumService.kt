package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.Album
import com.map.map.domain.entity.User
import com.map.map.domain.entity.backup.AlbumBackUp
import com.map.map.domain.response.album.AlbumListRo
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

interface AlbumService {
    fun makeAlbum(postAlbumDto: PostAlbumDto, userId: String)
    @Transactional(readOnly = true)
    fun getAlbumListLatest(id: Long?): List<AlbumListRo>
}