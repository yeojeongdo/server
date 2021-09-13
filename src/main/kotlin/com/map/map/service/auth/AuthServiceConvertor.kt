package com.map.map.service.auth

import com.map.map.domain.dto.auth.RegisterDto
import com.map.map.domain.entity.User

fun registerDtoToUser(registerDto: RegisterDto, user: User){
    user.id = registerDto.id
    user.password = registerDto.password
    user.name = registerDto.name
    user.gender = registerDto.gender
    user.birthDate = registerDto.birthDate
}