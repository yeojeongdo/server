 package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.response.Response
import com.map.map.domain.response.ResponseData
import com.map.map.domain.response.album.AlbumDetailRo
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.service.album.AlbumService
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

 @RestController
@RequestMapping("album")
class AlbumController @Autowired constructor(
     private var albumService: AlbumService
) {

     @Value("\${server.port}")
     private val port: Int? = null

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
    fun getAlbumListLatest(@RequestParam  id : Long?, request: HttpServletRequest): ResponseData<List<AlbumListRo>> {
        val serverAddress = "${request.remoteAddr}:${port}"
        val data = albumService.getAlbumListLatest(id, serverAddress)
        return ResponseData(HttpStatus.OK, "성공", data)
    }
    @AutoLogging
    @GetMapping("/detail/{albumId}")
    @ApiOperation("앨범 자세히 보기")
    fun getAlbumDetail(@PathVariable albumId:Long, request: HttpServletRequest): ResponseData<AlbumDetailRo>{
        val serverAddress = "${request.remoteAddr}:${port}"
        val data = albumService.getAlbumDetail(albumId, serverAddress)
        return ResponseData(HttpStatus.OK, "성공", data)
    }

     @AutoLogging
     @GetMapping("/users/{userIdx}")
     @ApiOperation("유저의 앨범 리스트 받아오기")
     fun getUserPage(@PathVariable userIdx: Long, @RequestParam lastAlbumId:Long?, request: HttpServletRequest) : ResponseData<List<AlbumListRo>> {
         val serverAddress = "${request.remoteAddr}:${port}"
         val albumList = albumService.getUserAlbumList(userIdx, lastAlbumId, serverAddress)
         return ResponseData(HttpStatus.OK, "성공", albumList)
     }
}