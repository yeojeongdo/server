package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.Building

fun postAlbumDtoToBuilding(postAlbumDto: PostAlbumDto, building: Building){
    building.address = postAlbumDto.address
    building.latitude = postAlbumDto.latitude
    building.longitude = postAlbumDto.longitude
}