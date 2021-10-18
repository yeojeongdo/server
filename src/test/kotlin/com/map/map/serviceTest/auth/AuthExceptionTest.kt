package com.map.map.serviceTest.auth

import com.map.map.domain.dto.auth.LoginDto
import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.enum.Gender
import com.map.map.exception.CustomHttpException
import com.map.map.service.auth.AuthServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.util.*

@SpringBootTest
class AuthExceptionTest {
    @Autowired
    private lateinit var authServiceImpl: AuthServiceImpl
    /**
     * exception 발생
     */
    @Test
    @Transactional
    fun registerExistTest(){
        try{
            var registerDto: RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)
            authServiceImpl.register(registerDto)
        }catch (e: CustomHttpException){
            e.printStackTrace()
            assert(e.status == HttpStatus.FORBIDDEN)
        }
    }

    @Test
    @Transactional
    fun userNotExistTest(){
        try{
            var registerDto: RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)

            var id = "aa"
            authServiceImpl.checkId(id)
        }catch (e: CustomHttpException){
            e.printStackTrace()
            assert(e.status == HttpStatus.NOT_FOUND)
        }
    }

    @Test
    @Transactional
    fun LoginNotFoundTest(){
        try{
            var registerDto: RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)

            var loginDto = LoginDto()
            loginDto.id = "qe"
            loginDto.password = "qwewqewqe"
            authServiceImpl.login(loginDto)
        }catch (e: CustomHttpException){
            e.printStackTrace()
            if(e.status == HttpStatus.NOT_FOUND){
                assert(e.status == HttpStatus.NOT_FOUND)
            }else{
                throw e
            }
        }
    }

    @Test
    @Transactional
    fun LoginDifferentPasswordTest(){
        try{
            var registerDto: RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)

            var loginDto = LoginDto()
            loginDto.id = "qwe"
            loginDto.password = "qwewqewqe"
            authServiceImpl.login(loginDto)
        }catch (e: CustomHttpException){
            e.printStackTrace()
            if(e.status == HttpStatus.BAD_REQUEST){
                assert(e.status == HttpStatus.BAD_REQUEST)
            }else{
                throw e
            }
        }
    }
}