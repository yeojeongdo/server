package com.map.map.service.building

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.Building
import com.map.map.domain.repository.BuildingRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BuildingServiceImpl @Autowired constructor(
    private var buildingRepo: BuildingRepo
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
}