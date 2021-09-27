package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.BuildingRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.repository.VisitedRepo
import com.map.map.exception.CustomHttpException
import com.map.map.service.building.BuildingService
import com.map.map.service.file.FileServiceImpl
import com.map.map.service.photo.PhotoService
import com.map.map.service.visited.VisitedService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
class AlbumServiceImpl @Autowired constructor(
    private var albumRepo: AlbumRepo,
    private var buildingService: BuildingService,
    private var visitedService: VisitedService,
    private var photoService: PhotoService,
    private var buildingRepo: BuildingRepo,
    private var visitedRepo: VisitedRepo,
    private var userRepo: UserRepo,

) : AlbumService {

    /**
     * 앨범 생성
     */
    @Transactional
    override fun makeAlbum(postAlbumDto: PostAlbumDto, userId: String) {
        try{
            var user = getUser(userId)

            var building = buildingService.setBuilding(postAlbumDto)

            val visited = visitedService.setVisited(building, user)

            val photoList : MutableList<Photo> = photoService.saveFiles(postAlbumDto.files)

            val album = Album()
            setAlbum(album, postAlbumDto, user, building, photoList)


            buildingRepo.save(building)
            visitedRepo.save(visited)
            albumRepo.save(album)
        }catch (e: Exception){
            throw e;
        }
    }

    private fun getUser(userId: String): User{
        var user = userRepo.findById(userId)
        if(user == null){
            throw CustomHttpException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        }
        return user
    }
}