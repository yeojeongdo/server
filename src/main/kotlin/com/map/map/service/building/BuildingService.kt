package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.Building
import com.map.map.domain.entity.backup.BuildingBackUp

interface BuildingService {
    fun setBuilding(postAlbumDto: PostAlbumDto): Building
}