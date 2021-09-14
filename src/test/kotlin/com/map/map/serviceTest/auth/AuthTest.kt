package com.map.map.serviceTest.auth

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.enum.Gender
import com.map.map.service.auth.AuthServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest(classes = arrayOf(AuthServiceImpl::class))
class AuthTest {
    @Autowired
    private lateinit var authServiceImpl: AuthServiceImpl

    @Test
    @Transactional
    fun registerSaveTest1(){
        try{
            var registerDto:RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    @Test
    @Transactional
    fun userExistTest(){
        try{
            var registerDto:RegisterDto = RegisterDto()
            registerDto.id= "qwe"
            registerDto.password = "qwerqwer"
            registerDto.gender = Gender.Male
            registerDto.name = "qqqq"
            registerDto.birthDate = Date()

            authServiceImpl.register(registerDto)

            authServiceImpl.checkId(registerDto.id!!)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }





}