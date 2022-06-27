package com.map.map.service.user

import com.map.map.domain.entity.User
import com.map.map.domain.response.user.UserInfoRo
import kotlin.streams.toList

fun userToUserInfoRo(user: User, userInfoRo: UserInfoRo, serverAddress: String) {
    userInfoRo.id = user.idx
    userInfoRo.name = user.name
    userInfoRo.image = if (!user.image.equals(null)) "${serverAddress}${user.image}" else user.image
    userInfoRo.gender = user.gender
    userInfoRo.birthDay = user.birthDate
}

fun changeUserListToUserInfoList(users: List<User>, serverAddress: String): List<UserInfoRo> {
    return users.stream().map {
        val userInfo = UserInfoRo()
        userToUserInfoRo(it, userInfo, serverAddress)
        userInfo
    }.toList()
}