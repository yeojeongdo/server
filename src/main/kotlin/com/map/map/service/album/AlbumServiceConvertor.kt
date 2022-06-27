package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.entity.backup.AlbumBackUp
import com.map.map.domain.response.album.AlbumDetailRo
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.domain.response.building.BuildingInfoRo
import com.map.map.domain.response.user.UserInfoRo
import com.map.map.service.building.buildingToBuildingInfo
import com.map.map.service.user.userToUserInfoRo
import kotlin.streams.toList


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

fun albumToAlbumListRo(albumListRo: AlbumListRo, album: Album, serverAddress: String) {
    albumListRo.id = album.idx
    albumListRo.createDate = album.date

    albumListRo.photo = serverAddress + album.photo[0].filed

    albumListRo.user = UserInfoRo()
    userToUserInfoRo(album.user!!, albumListRo.user!!, serverAddress)


    albumListRo.building = BuildingInfoRo()
    buildingToBuildingInfo(album.building!!, albumListRo.building!!)
}

fun albumToAlbumDetail(album: Album, commentNum: Long, likeNum: Long, albumDetailRo: AlbumDetailRo, serverAddress: String){
    albumDetailRo.id = album.idx
    albumDetailRo.createDate = album.date
    albumDetailRo.memo = album.memo
    albumDetailRo.commentNum = commentNum
    albumDetailRo.likeNum = likeNum

    album.photo.stream().forEach{
        albumDetailRo.photo.add(serverAddress + it.filed!!)
    }

    albumDetailRo.user = UserInfoRo()
    userToUserInfoRo(album.user!!, albumDetailRo.user!!, serverAddress)

    albumDetailRo.building = BuildingInfoRo()
    buildingToBuildingInfo(album.building!!, albumDetailRo.building!!)
}

/**
 * AlbumListRo 를 리스트로 만들어주기
 */
fun albumListRoToList(albums: MutableList<Album>, serverAddress: String): List<AlbumListRo> {
    return albums.stream().map {
        var albumListRo = AlbumListRo()
        albumToAlbumListRo(albumListRo, it, serverAddress)
        albumListRo
    }.toList()
}
