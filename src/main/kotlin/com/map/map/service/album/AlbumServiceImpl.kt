package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.repository.*
import com.map.map.domain.repository.paging.AlbumListRepo
import com.map.map.domain.response.album.AlbumDetailRo
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.exception.CustomHttpException
import com.map.map.service.building.BuildingService
import com.map.map.service.file.FileServiceImpl
import com.map.map.service.photo.PhotoService
import com.map.map.service.user.UserService
import com.map.map.service.visited.VisitedService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumServiceImpl @Autowired constructor(
    private var albumRepo: AlbumRepo,
    private var buildingService: BuildingService,
    private var visitedService: VisitedService,
    private var photoService: PhotoService,
    private var buildingRepo: BuildingRepo,
    private var visitedRepo: VisitedRepo,
    private var userRepo: UserRepo,
    private var commentRepo: CommentRepo,
    private var likeRepo: LikeRepo,
    private var albumListRepo: AlbumListRepo,
    private var userService: UserService

) : AlbumService {

    /**
     * 앨범 생성
     */
    @Transactional
    override fun makeAlbum(postAlbumDto: PostAlbumDto, userId: String) {
        try{
            var user = userService.getUser(userId)

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

    /**
     * 엘범 최신순 보기
     */
    @Transactional(readOnly = true)
    override fun getAlbumListLatest(id: Long?): List<AlbumListRo> {
        var albumList : MutableList<Album>? = null
        if(id == null){
            albumList = albumRepo.findTop10ByOrderByIdxDesc()
        }else{
           albumList = albumRepo.findTop10ByIdxLessThanOrderByIdxDesc(id)
        }

        return this.albumListRoToList(albumList)
    }

    /**
     * 엘범 상세 정보
     */
    override fun getAlbumDetail(id: Long): AlbumDetailRo {
        var album : Album = findAlbum(id)

        var commentNum : Long = commentRepo.countCommentNum(id)
        var likeNum: Long = likeRepo.countAlbumLikeNum(id)

        var albumDetailRo = AlbumDetailRo()
        albumToAlbumDetail(album, commentNum, likeNum, albumDetailRo)
        return albumDetailRo
    }

    /**
     * AlbumListRo 를 리스트로 만들어주기
     */
    fun albumListRoToList(albums: MutableList<Album>): List<AlbumListRo> {
        var list = mutableListOf<AlbumListRo>()

        for (album in albums) {
            var albumListRo = AlbumListRo()
            albumToAlbumListRo(albumListRo, album)

            list.add(albumListRo)
        }

        return list
    }

    override fun findAlbum(id:Long) : Album {
        return albumRepo.findByIdx(id) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "앨범을 찾을 수 없음")

    }
}