package com.map.map.service.auth

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.entity.User
import com.map.map.domain.repository.UserRepo
import com.map.map.lib.Crypto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException

@Service
class AuthServiceImpl @Autowired constructor(
    private val crypto : Crypto,
    private val userRepo: UserRepo
): AuthService {

    @Transactional
    override fun register(registerDto: RegisterDto){
        try{
            var user:User = User()
            registerDtoToUser(registerDto, user)

            if(checkExistId(user.id!!)){
                throw HttpClientErrorException(HttpStatus.FORBIDDEN, "이미 존재하는 유저")
            }

            user.password = crypto.sha256(user.password!!)

            userRepo.save(user)
        }catch (e: Exception){
            throw e;
        }
    }

    override fun checkId(id: String) {
        if(checkExistId(id)){
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "이미 존재하는 유저")
        }
    }

    private fun checkExistId (id: String): Boolean {
        var existUser = userRepo.findById(id)
        return (existUser != null)
    }
}