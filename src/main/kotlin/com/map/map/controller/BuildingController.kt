package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.building.GetBuildingsAlbumListDto
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.service.building.BuildingService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/building")
class BuildingController @Autowired constructor(
    private var buildingService: BuildingService
) {
    @AutoLogging
    @GetMapping("albums")
    @ApiOperation("건물의 앨범 리스트 받아오기")
    @ApiImplicitParams()
    fun getBuildingsAlbumList(@ModelAttribute @Valid getBuildingsAlbumListDto: GetBuildingsAlbumListDto) : ResponseData<List<AlbumListRo>>{
        val albumList = buildingService.getAlbum(getBuildingsAlbumListDto)
        return ResponseData(HttpStatus.OK, "성공", albumList)
    }
}