package com.map.map.service.file

import com.map.map.exception.CustomHttpException
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.*
import java.util.*

@Service
class FileServiceImpl() : FileService{
    private var fileStorageLocation: Path = Paths.get("static/").toAbsolutePath().normalize()

    /**
     * 파일 저장
     */
    override fun storeFile(file: MultipartFile): String {
        if (file.isEmpty || (FilenameUtils.getExtension(file.originalFilename) != "png"
                    && FilenameUtils.getExtension(file.originalFilename) != "jpg"
                    && FilenameUtils.getExtension(file.originalFilename) != "jpeg"
                    && FilenameUtils.getExtension(file.originalFilename) != "gif"
                    && FilenameUtils.getExtension(file.originalFilename) != "svg")) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
        }
        val fileName = StringUtils.cleanPath(UUID.randomUUID().toString()+"-"+Objects.nonNull(file.originalFilename)+".jpg")
        return try{
            var makeFile = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, makeFile, StandardCopyOption.REPLACE_EXISTING)
            fileName
        }catch (e: IOException){
            throw e
        }
    }

    override fun loadFile(fileName: String): Resource {
       try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource : Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                return resource
            } else {
                throw CustomHttpException(HttpStatus.NOT_FOUND, "없는 파일.")
            }
        }
        catch (e : Exception){
            throw e
        }

    }


}