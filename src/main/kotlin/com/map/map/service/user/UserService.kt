package com.map.map.service.user

import com.map.map.domain.dto.user.DeleteUserDto
import com.map.map.domain.dto.user.PatchUserBirthDateDto
import com.map.map.domain.dto.user.PatchUserNameDto
import com.map.map.domain.entity.User
import com.map.map.domain.response.user.UserInfoRo
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun changeUserName(patchUserNameDto: PatchUserNameDto, userId: String)
    fun changeUserBirthDate(patchUserBirthDateDto: PatchUserBirthDateDto, userId: String)
    fun deleteUser(deleteUserDto: DeleteUserDto, userId: String)
    @Transactional(readOnly = true)
    fun getUserInfo(userId: String): UserInfoRo
    fun changeUserImage(file: MultipartFile, userId: String)
}