package com.map.map.service.auth

import com.map.map.domain.dto.auth.LoginDto
import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.entity.User
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.response.auth.LoginRo
import com.map.map.enum.JwtType
import com.map.map.lib.Crypto
import com.map.map.service.jwt.JwtService
import com.map.map.service.jwt.JwtServiceImpl
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException

@Service
class AuthServiceImpl @Autowired constructor(
    private val crypto: Crypto,
    private val userRepo: UserRepo,
    private val jwtService: JwtServiceImpl
) : AuthService {

    @Transactional
    override fun register(registerDto: RegisterDto) {
        try {
            var user: User = User()
            registerDtoToUser(registerDto, user)

            if (checkExistId(user.id!!)) {
                throw HttpClientErrorException(HttpStatus.FORBIDDEN, "이미 존재하는 유저입니다.")
            }

            user.password = crypto.sha256(user.password!!)

            userRepo.save(user)
        } catch (e: Exception) {
            throw e;
        }
    }

    override fun checkId(id: String) {
        if (checkExistId(id)) {
            throw HttpClientErrorException(HttpStatus.FORBIDDEN, "이미 존재하는 유저입니다.")
        }
    }

    override fun login(loginDto: LoginDto): LoginRo {
        try {
            checkExistIdAndPassword(loginDto.id!!, loginDto.password!!)

            return makeJwt(loginDto.id!!)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun checkExistIdAndPassword(id: String, password: String){
        val findUser = userRepo.findById(id);
        if(findUser == null){
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        }

        if(findUser.password != crypto.sha256(password)){
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.")
        }
    }

    private fun makeJwt(id: String): LoginRo{
        val loginRo = LoginRo()
        loginRo.accessToken = jwtService.createToken(id, JwtType.ACCESS)
        loginRo.refreshToken = jwtService.createToken(id, JwtType.REFRESH)
        return loginRo
    }

    private fun checkExistId(id: String): Boolean {
        var existUser = userRepo.findById(id)
        return (existUser != null)
    }
}