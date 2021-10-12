package com.map.map.service.follow

interface FollowService {
    fun changeFollowState(userId:String, userIdx: Long)
}