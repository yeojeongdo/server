package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.dto.building.GetBuildingUserListDto
import com.map.map.domain.dto.building.GetBuildingsAlbumListDto
import com.map.map.domain.dto.building.SearchBuildingDto
import com.map.map.domain.entity.Building
import com.map.map.domain.entity.backup.BuildingBackUp
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.domain.response.building.BuildingInfoRo
import com.map.map.domain.response.user.UserInfoRo

interface BuildingService {
    fun setBuilding(postAlbumDto: PostAlbumDto): Building
    fun getAlbum(getBuildingsAlbumListDto: GetBuildingsAlbumListDto, serverAddress: String) : List<AlbumListRo>
    fun getUser(getBuildingUserListDto: GetBuildingUserListDto, serverAddress: String) : List<UserInfoRo>
    fun searchBuilding(searchBuildingDto: SearchBuildingDto) : List<BuildingInfoRo>
}