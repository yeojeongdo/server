package com.map.map.service.follow

import com.map.map.domain.dto.follow.FollowNumbersDto

interface FollowService {
    fun changeFollowState(userId:String, userIdx: Long)
    fun getFollowNum(userIdx: Long) : FollowNumbersDto
}