package com.map.map.controller

import com.map.map.service.file.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpServerErrorException
import java.io.IOException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/file")
class FileController @Autowired constructor(
    private var fileService: FileService
) {
    @GetMapping("/{fileName}")
    fun getImage(@PathVariable fileName: String, request : HttpServletRequest) :  ResponseEntity<*>?{
        val resource: Resource = fileService.loadFile(fileName)
        val contentType: String = try {
            request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (e: IOException) {
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource)
    }
}