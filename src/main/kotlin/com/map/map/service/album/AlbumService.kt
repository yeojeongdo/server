package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.User
import org.springframework.web.multipart.MultipartFile

interface AlbumService {
    fun makeAlbum(postAlbumDto: PostAlbumDto, userId: String)
}