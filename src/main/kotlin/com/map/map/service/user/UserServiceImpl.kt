package com.map.map.service.user

import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.entity.backup.UserBackUp
import com.map.map.domain.entity.backup.repo.UserBackUpRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.lib.Crypto
import com.map.map.service.auth.userToUserBackUp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(
    private var userRepo: UserRepo,
    private var userBackUpRepo : UserBackUpRepo,
    private var crypto: Crypto
) : UserService {

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
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.")

            updateDeletedUserBackupData(user)
        }catch (e : Exception){
            throw e
        }
    }

    private fun getUser(userId: String): User{
        var user = userRepo.findById(userId)
        if(user == null){
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        }
        return user
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