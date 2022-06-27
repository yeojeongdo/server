package com.map.map.service.album

import com.map.map.domain.dto.album.PostAlbumDto
import com.map.map.domain.entity.*
import com.map.map.domain.repository.*
import com.map.map.domain.repository.querydsl.CommentQuery
import com.map.map.domain.repository.querydsl.LikeQuery
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
    private var commentQuery: CommentQuery,
    private var likeQuery: LikeQuery,
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
            buildingRepo.save(building)

            val visited = visitedService.setVisited(building, user)
            visitedRepo.save(visited)

            val photoList : MutableList<Photo> = photoService.saveFiles(postAlbumDto.files)

            val album = Album()
            setAlbum(album, postAlbumDto, user, building, photoList)

            albumRepo.save(album)
        }catch (e: Exception){
            throw e;
        }
    }

    /**
     * 엘범 최신순 보기
     */
    @Transactional(readOnly = true)
    override fun getAlbumListLatest(id: Long?, serverAddress: String): List<AlbumListRo> {
        var albumList : MutableList<Album>? = null
        if(id == null){
            albumList = albumRepo.findTop10ByOrderByIdxDesc()
        }else{
           albumList = albumRepo.findTop10ByIdxLessThanOrderByIdxDesc(id)
        }

        return albumListRoToList(albumList, serverAddress)
    }

    @Transactional(readOnly = true)
    override fun getUserAlbumList(userIdx: Long, lastAlbumId: Long?, serverAddress: String): List<AlbumListRo> {
        val user = userService.getUser(userIdx)
        return getUsersAlbumListLatest(user, lastAlbumId, serverAddress)
    }

    override fun getUsersAlbumListLatest(user: User, id: Long?, serverAddress: String): List<AlbumListRo> {
        var albumList : MutableList<Album>? = null
        if(id == null){
            albumList = albumRepo.findTop10ByUserOrderByIdxDesc(user)
        }else{
            albumList = albumRepo.findTop10ByUserAndIdxLessThanOrderByIdxDesc(user, id)
        }

        return albumListRoToList(albumList, serverAddress)
    }

    /**
     * 엘범 상세 정보
     */
    override fun getAlbumDetail(id: Long, serverAddress: String): AlbumDetailRo {
        var album : Album = findAlbum(id)

        var commentNum : Long = commentQuery.getCommentNum(id)
        var likeNum: Long = likeQuery.getLikeNum(id)

        var albumDetailRo = AlbumDetailRo()
        albumToAlbumDetail(album, commentNum, likeNum, albumDetailRo, serverAddress)
        return albumDetailRo
    }

    override fun findAlbum(id:Long) : Album {
        return albumRepo.findByIdx(id) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "앨범을 찾을 수 없음")

    }


}