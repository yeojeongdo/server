package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.entity.backup.AlbumBackUp



fun userAndBuildingToVisited(building: Building, user: User, visited: Visited){
    building.visitor.add(visited)
    visited.building = building
    user.visited.add(visited)
    visited.user = user
}

fun setAlbum(album : Album, postAlbumDto: PostAlbumDto, user: User, building: Building, photoList: MutableList<Photo>){
    album.memo = postAlbumDto.memo!!
    album.photo = photoList

    user.albums.add(album)
    album.user = user

    building.albums.add(album)
    album.building = building
}