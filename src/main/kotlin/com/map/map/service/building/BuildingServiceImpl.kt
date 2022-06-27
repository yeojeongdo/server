package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.dto.building.GetBuildingUserListDto
import com.map.map.domain.dto.building.GetBuildingsAlbumListDto
import com.map.map.domain.dto.building.SearchBuildingDto
import com.map.map.domain.entity.Album
import com.map.map.domain.entity.Building
import com.map.map.domain.entity.User
import com.map.map.domain.repository.BuildingRepo
import com.map.map.domain.repository.querydsl.BuildingQuery
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.domain.response.building.BuildingInfoRo
import com.map.map.domain.response.user.UserInfoRo
import com.map.map.exception.CustomHttpException
import com.map.map.service.album.albumToAlbumListRo
import com.map.map.service.user.UserService
import com.map.map.service.user.userToUserInfoRo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.streams.toList

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

    override fun getAlbum(getBuildingsAlbumListDto: GetBuildingsAlbumListDto, serverAddress: String): List<AlbumListRo> {
        checkBuildingExist(getBuildingsAlbumListDto.address!!)

        val albums = buildingQuery.getAlbums(getBuildingsAlbumListDto.address!!, getBuildingsAlbumListDto.lastAlbumId)

        return albums.stream().map {
            val albumListRo = AlbumListRo()
            albumToAlbumListRo(albumListRo, it, serverAddress)
            albumListRo
        }.toList()
//        for(album:Album in albums){
//            val albumListRo = AlbumListRo()
//            albumToAlbumListRo(albumListRo, album)
//            albumList.add(albumListRo)
//        }
//
//        return albumList
    }

    override fun getUser(getBuildingUserListDto: GetBuildingUserListDto, serverAddress: String): List<UserInfoRo> {
        checkBuildingExist(getBuildingUserListDto.address!!)

        val users = buildingQuery.getUsers(getBuildingUserListDto.address!!, getBuildingUserListDto.lastUserId)

        return users.stream().map {
            val userInfoRo = UserInfoRo();
            userToUserInfoRo(it, userInfoRo, serverAddress)
            userInfoRo
        }.toList()
    }

    override fun searchBuilding(searchBuildingDto: SearchBuildingDto): List<BuildingInfoRo> {
        searchBuildingDto.value = "%"+searchBuildingDto.value+"%"
        val buildings = buildingQuery.searchBuilding(searchBuildingDto)

        return buildings.stream().map {
            val buildingInfoRo = BuildingInfoRo();
            buildingToBuildingInfo(it, buildingInfoRo)
            buildingInfoRo
        }.toList()
    }

    private fun checkBuildingExist(address:String) : Building {
        return buildingRepo.findByAddress(address)
            ?: throw CustomHttpException(HttpStatus.NOT_FOUND,"건물을 찾을 수 없습니다.")
    }

}