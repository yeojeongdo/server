package com.map.map.service.user

import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.entity.backup.UserBackUp
import com.map.map.domain.entity.backup.repo.UserBackUpRepo
import com.map.map.domain.repository.UserRepo
import com.map.map.service.auth.userToUserBackUp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(
    private var userRepo: UserRepo,
    private var userBackUpRepo : UserBackUpRepo
) : UserService {

    @Transactional
    override fun changeUserName(patchUserNameDto: PatchUserNameDto, user: User) {
        try{
            user.name = patchUserNameDto.name
            val savedUser = userRepo.save(user)
            updateUserBackUpData(savedUser)
        }catch (e : Exception){
            throw e
        }
    }

    private fun updateUserBackUpData(user: User){
        val userBackUp = UserBackUp()
        userToUserBackUp(user, userBackUp)
        userBackUp.updatedDate = Date()
        userBackUpRepo.save(userBackUp)
    }

}