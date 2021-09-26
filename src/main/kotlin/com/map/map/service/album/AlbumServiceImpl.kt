package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.BuildingRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.repository.VisitedRepo
import com.map.map.service.file.FileServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
class AlbumServiceImpl @Autowired constructor(
    private var albumRepo: AlbumRepo,
    private var buildingRepo: BuildingRepo,
    private var visitedRepo: VisitedRepo,
    private var userRepo: UserRepo,
    private var fileService: FileServiceImpl
) : AlbumService {

    @Transactional()
    override fun makeAlbum(postAlbumDto: PostAlbumDto, userId: String) {
        try{
            var user = getUser(userId)

            var building = setBuilding(postAlbumDto)

            val visited = setVisited(building, user)

            val photoList : MutableList<Photo> = saveFile(postAlbumDto.files)

            val album = Album()
            setAlbum(album, postAlbumDto, user, building, photoList)

            visitedRepo.save(visited)
            buildingRepo.save(building)
            albumRepo.save(album)
        }catch (e: Exception){
            throw e;
        }
    }

    private fun setBuilding(postAlbumDto: PostAlbumDto): Building{
        var building =  buildingRepo.findByAddress(postAlbumDto.address!!)
        if(building == null){
            building = Building()
            postAlbumDtoToBuilding(postAlbumDto, building)
        }

        return building
    }

    private fun setVisited(building: Building, user:User): Visited{
        val visited = Visited()
        userAndBuildingToVisited(building, user, visited)

        return visited
    }

    private fun saveFile(files : List<MultipartFile>) : MutableList<Photo>{
        val photoList : MutableList<Photo> = mutableListOf()
        for(file: MultipartFile in files){
            val fileName = fileService.storeFile(file)
            val photo = Photo()
            photo.filed = fileName

            photoList.add(photo)
        }

        return photoList
    }

    private fun getUser(userId: String): User{
        var user = userRepo.findById(userId)
        if(user == null){
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        }
        return user
    }

}