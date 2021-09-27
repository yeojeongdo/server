package com.map.map.service.photo

import com.map.map.domain.entity.Photo
import org.springframework.web.multipart.MultipartFile

interface PhotoService {
    fun saveFiles(files : List<MultipartFile>) : MutableList<Photo>
}