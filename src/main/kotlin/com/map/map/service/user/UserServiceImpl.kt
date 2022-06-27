package com.map.map.service.user

import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.entity.backup.UserBackUp
import com.map.map.domain.entity.backup.repo.UserBackUpRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.repository.querydsl.UserQuery
import com.map.map.domain.response.album.AlbumListRo
import com.map.map.domain.response.user.UserInfoRo
import com.map.map.exception.CustomHttpException
import com.map.map.lib.Crypto
import com.map.map.service.album.AlbumService
import com.map.map.service.auth.userToUserBackUp
import com.map.map.service.file.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.streams.toList

@Service
class UserServiceImpl @Autowired constructor(
    private var userRepo: UserRepo,
    private var userBackUpRepo : UserBackUpRepo,
    private var crypto: Crypto,
    private var fileService: FileService,
    private var userQuery: UserQuery

) : UserService {
    @Value("\${this.server.address}")
    private val serverAddress : String? = null

    /**
     * 유저 이름 변경
     */
    @Transactional
    override fun changeUserName(patchUserNameDto: PatchUserNameDto, userId: String) {
        try{
            val user = getUser(userId)
            user.name = patchUserNameDto.name
            val savedUser = userRepo.save(user)
            updateUserBackUpData(savedUser)
        }catch (e : Exception){
            throw e
        }
    }

    /**
     * 유저 생일 변경
     */
    @Transactional
    override fun changeUserBirthDate(patchUserBirthDateDto: PatchUserBirthDateDto, userId: String) {
        try{
            val user = getUser(userId)
            user.birthDate = patchUserBirthDateDto.birthDate
            val saveUser = userRepo.save(user)
            updateUserBackUpData(saveUser)
        }catch (e : Exception){
            throw e
        }
    }

    /**
     * 유저 삭제
     */
    @Transactional
    override fun deleteUser(deleteUserDto: DeleteUserDto, userId: String) {
        try{
            val user = getUser(userId)
            var password = crypto.sha256(deleteUserDto.password!!)

            if(user.password!!.equals(password))
                userRepo.deleteByIdx(user.idx!!)
            else
                throw CustomHttpException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.")

            updateDeletedUserBackupData(user)
        }catch (e : Exception){
            throw e
        }
    }

    /**
     * 유저 정보 받아오기
     */
    @Transactional(readOnly = true)
    override fun getUserInfo(userIdx: Long?, userId: String, serverAddress: String): UserInfoRo {

        val user = if (userIdx == null) {
            getUser(userId)
        } else {
            getUser(userIdx)
        }

        val response = UserInfoRo()
        userToUserInfoRo(user, response, serverAddress)

        return response
    }

    /**
     * 프로필 사진 변경
     */
    @Transactional
    override fun changeUserImage(file: MultipartFile, userId: String) {
        val user = getUser(userId)
        val fileName = fileService.storeFile(file)
        user.image = serverAddress+"/file/"+fileName
        userRepo.save(user)
    }

    override fun getUser(userId: String): User{
        return userRepo.findById(userId) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
    }

    override fun getUser(userIdx: Long): User{
        return userRepo.findByIdx(userIdx) ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
    }

    override fun getFollowers(userIdx: Long, lastId: Long?, serverAddress: String): List<UserInfoRo> {
        val users = userQuery.getUserFollower(userIdx, lastId)

        return changeUserListToUserInfoList(users, serverAddress)
    }

    override fun getFollowing(userIdx: Long, lastId: Long?, serverAddress: String): List<UserInfoRo> {
        val users = userQuery.getUserFollowing(userIdx, lastId)

        return changeUserListToUserInfoList(users, serverAddress)
    }

    override fun getAllFollowers(userIdx: Long, serverAddress: String): List<UserInfoRo> {
        val users = userQuery.getUserAllFollower(userIdx)

        return changeUserListToUserInfoList(users, serverAddress)
    }

    /**
     * 유저 데이터 업데이트
     */
    private fun updateUserBackUpData(user: User){
        val userBackUp = UserBackUp()
        userToUserBackUp(user, userBackUp)
        userBackUp.updatedDate = Date()
        userBackUpRepo.save(userBackUp)
    }

    /**
     * 유저 삭제 데이터 업데이트
     */
    private fun updateDeletedUserBackupData(user: User){
        val userBackUp = UserBackUp()
        userToUserBackUp(user, userBackUp)
        userBackUp.deletedDate = Date()
        userBackUpRepo.save(userBackUp)
    }



}