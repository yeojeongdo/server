package com.map.map.service.photo

import com.map.map.domain.entity.Photo
import com.map.map.service.file.FileServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoServiceImpl @Autowired constructor(
    private var fileService: FileServiceImpl,
): PhotoService{
    @Value("\${this.server.address}")
    private val serverAddress : String? = null

    override fun saveFiles(files : List<MultipartFile>) : MutableList<Photo>{
        val photoList : MutableList<Photo> = mutableListOf()
        for(file: MultipartFile in files){
            val fileName = fileService.storeFile(file)
            val photo = Photo()
            photo.filed = serverAddress + "/file/" + fileName

            photoList.add(photo)
        }

        return photoList
    }
}