package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.dto.building.GetBuildingsAlbumListDto
import com.map.map.domain.entity.Album
import com.map.map.domain.entity.Building
import com.map.map.domain.repository.BuildingRepo
import com.map.map.domain.repository.querydsl.BuildingQuery
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.exception.CustomHttpException
import com.map.map.service.album.albumToAlbumListRo
import com.map.map.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BuildingServiceImpl @Autowired constructor(
    private var buildingRepo: BuildingRepo,
    private var buildingQuery: BuildingQuery
) : BuildingService{

    /**
     * 빌딩 세팅
     */
    override fun setBuilding(postAlbumDto: PostAlbumDto) : Building {
        var building =  buildingRepo.findByAddress(postAlbumDto.address!!)
        if(building == null){
            building = Building()
            postAlbumDtoToBuilding(postAlbumDto, building)
        }

        return building
    }

    override fun getAlbum(getBuildingsAlbumListDto: GetBuildingsAlbumListDto): List<AlbumListRo> {
        buildingRepo.findByAddress(getBuildingsAlbumListDto.address!!)
            ?: throw CustomHttpException(HttpStatus.NOT_FOUND,"건물을 찾을 수 없습니다.")

        val albums = buildingQuery.getAlbums(getBuildingsAlbumListDto.address!!, getBuildingsAlbumListDto.lastAlbumId)

        val albumList : MutableList<AlbumListRo> = mutableListOf()
        for(album:Album in albums){
            val albumListRo = AlbumListRo()
            albumToAlbumListRo(albumListRo, album)
            albumList.add(albumListRo)
        }

        return albumList
    }
}