 package com.map.map.controller

import com.map.map.annotation.AutoLogging
import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.User
import com.map.map.domain.response.Response
import com.map.map.service.album.AlbumService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
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
        val user = request.getAttribute("user") as User
        albumService.makeAlbum(postAlbumDto, user)
        return Response(HttpStatus.OK, "성공 ")
    }


}