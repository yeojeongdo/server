package com.map.map.service.file

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun storeFile(file: MultipartFile): String
    fun loadFile(fileName:String) : Resource
}