package com.map.map.service.user

import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User

interface UserService {
    fun changeUserName(patchUserNameDto: PatchUserNameDto, user: User)
    fun changeUserBirthDate(patchUserBirthDateDto: PatchUserBirthDateDto, user: User)
}