package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.Building
import com.map.map.domain.response.building.BuildingInfoRo

fun postAlbumDtoToBuilding(postAlbumDto: PostAlbumDto, building: Building){
    building.address = postAlbumDto.address
    building.latitude = postAlbumDto.latitude
    building.longitude = postAlbumDto.longitude
}

fun buildingToBuildingInfo(building: Building, buildingInfoRo: BuildingInfoRo){
    buildingInfoRo.id = building.id
    buildingInfoRo.address = building.address
    buildingInfoRo.latitude = building.latitude
    buildingInfoRo.longitude = building.longitude
}