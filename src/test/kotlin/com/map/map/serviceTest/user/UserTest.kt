package com.map.map.serviceTest.user

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.repository.UserRepo
import com.map.map.enum.Gender
import com.map.map.service.auth.AuthServiceImpl
import com.map.map.service.user.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest

class UserTest {
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var authServiceImpl: AuthServiceImpl
    @Autowired
    private lateinit var userRepo: UserRepo


    @Test
    @Transactional
    fun changeUserName(){
        var registerDto: RegisterDto = RegisterDto()
        setUser1(registerDto)

        authServiceImpl.register(registerDto)

        val user = userRepo.findById(registerDto.id!!)
        val beforeUserName = user!!.name!!

        var patchUserNameDto = PatchUserNameDto()
        patchUserNameDto.name = beforeUserName + "123"
        userService.changeUserName(patchUserNameDto, user)

        val changedUser = userRepo.findById(registerDto.id!!)
        val afterUserName = changedUser!!.name!!

        assert(beforeUserName+"123" == afterUserName)
    }


    @Test
    @Transactional
    fun changeUserBirthDate(){
        var registerDto: RegisterDto = RegisterDto()
        setUser1(registerDto)

        authServiceImpl.register(registerDto)

        val user = userRepo.findById(registerDto.id!!)
        val beforeUserBirthDate = user!!.birthDate!!

        var patchUserBirthDateDto = PatchUserBirthDateDto()
        patchUserBirthDateDto.birthDate = Date(beforeUserBirthDate.time + 1000)
        userService.changeUserBirthDate(patchUserBirthDateDto, user)

        val changedUser = userRepo.findById(registerDto.id!!)
        val afterUserBirthDate = changedUser!!.birthDate

        assert(Date(beforeUserBirthDate.time + 1000) == afterUserBirthDate)
    }

    @Test
    @Transactional
    fun deleteUser(){
        var registerDto: RegisterDto = RegisterDto()
        setUser1(registerDto)

        authServiceImpl.register(registerDto)
        val user = userRepo.findById(registerDto.id!!)

        var deleteUserDto = DeleteUserDto()
        deleteUserDto.password = registerDto.password

        userService.deleteUser(deleteUserDto, user!!)
        assert(userRepo.findById(registerDto.id!!) == null)
    }


    fun setUser1(registerDto: RegisterDto){
        registerDto.id= "qwe"
        registerDto.password = "qwerqwer"
        registerDto.gender = Gender.Male
        registerDto.name = "qqqq"
        registerDto.birthDate = Date()

    }
}