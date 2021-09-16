package com.map.map.service.user

import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User

interface UserService {
    fun changeUserName(patchUserNameDto: PatchUserNameDto, user: User)
}