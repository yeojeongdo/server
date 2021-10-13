package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.dto.building.GetBuildingsAlbumListDto
import com.map.map.domain.entity.Building
import com.map.map.domain.entity.backup.BuildingBackUp
import com.map.map.domain.response.album.AlbumListRo

interface BuildingService {
    fun setBuilding(postAlbumDto: PostAlbumDto): Building
    fun getAlbum(getBuildingsAlbumListDto: GetBuildingsAlbumListDto) : List<AlbumListRo>
}