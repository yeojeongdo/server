package com.map.map.service.user

import com.map.map.domain.entity.User
import com.map.map.domain.response.user.UserInfoRo

fun userToUserInfoRo(user: User, userInfoRo: UserInfoRo) {
    userInfoRo.id = user.id
    userInfoRo.name = user.name
    userInfoRo.gender = user.gender
    userInfoRo.birthDay = user.birthDate
}