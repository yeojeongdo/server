package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.repository.AlbumRepo
import com.map.map.domain.repository.BuildingRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.repository.VisitedRepo
import com.map.map.service.file.FileServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
    override fun makeAlbum(postAlbumDto: PostAlbumDto, user: User) {
        try{
            var user2 = userRepo.findById(user.id!!)!!

            var building = buildingRepo.findByAddress(postAlbumDto.address!!)

            if(building == null){
                building = Building()
                postAlbumDtoToBuilding(postAlbumDto, building)
            }

            val visited = Visited()
            building.visitor.add(visited)
            visited.building = building
            user2.visited.add(visited)
            visited.user = user2

            val photoList : MutableList<Photo> = mutableListOf()
            for(file: MultipartFile in postAlbumDto.files){
                val fileName = fileService.storeFile(file)
                val photo = Photo()
                photo.filed = fileName

                photoList.add(photo)
            }

            val album = Album()
            album.memo = postAlbumDto.memo!!
            album.photo = photoList
            user2.albums.add(album)
            album.user = user2

            building.albums.add(album)
            album.building = building

            buildingRepo.save(building)
            albumRepo.save(album)
        }catch (e: Exception){
            throw e;
        }
    }


}