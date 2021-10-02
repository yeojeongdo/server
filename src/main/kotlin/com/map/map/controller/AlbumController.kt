 package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.User
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.album.AlbumDetailRo
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.service.album.AlbumService
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

 @RestController
@RequestMapping("album")
class AlbumController @Autowired constructor(
     private var albumService: AlbumService
) {
    @AutoLogging
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperation("앨범 생성")
    fun makeAlbum(
        @ModelAttribute @Valid postAlbumDto: PostAlbumDto,
        request : HttpServletRequest): Response {
        val userId = request.getAttribute("userId") as String
        albumService.makeAlbum(postAlbumDto, userId)
        return Response(HttpStatus.OK, "성공 ")
    }

    @AutoLogging
    @GetMapping("/latest")
    @ApiOperation("앨범 최신순 보기")
    @ApiModelProperty(required = false, value = "id")
    fun getAlbumListLatest(@RequestParam  id : Long?): ResponseData<List<AlbumListRo>> {
        val data = albumService.getAlbumListLatest(id)
        return ResponseData(HttpStatus.OK, "성공", data)
    }
    @AutoLogging
    @GetMapping("/detail/{albumId}")
    @ApiOperation("앨범 자세히 보기")
    fun getAlbumDetail(@PathVariable albumId:Long, request: HttpServletRequest): ResponseData<AlbumDetailRo>{
        val userId = request.getAttribute("userId") as String
        val data = albumService.getAlbumDetail(albumId)
        return ResponseData(HttpStatus.OK, "성공", data)
    }
}