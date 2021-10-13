package com.map.map.service.like

import com.map.map.domain.entity.User
import com.map.map.domain.response.like.LikedUsersRo

fun userToSimpleUserInfoRo(user: User, likedUsersRo: LikedUsersRo) {
    likedUsersRo.id = user.id
    likedUsersRo.profileImg = user.image
}