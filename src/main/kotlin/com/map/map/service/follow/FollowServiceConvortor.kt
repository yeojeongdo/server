package com.map.map.service.follow

import com.map.map.domain.dto.follow.FollowNumbersDto

fun followerNumAndFollowingNumToFollowNumDto(followerNum: Long, followingNum: Long, followNumbersDto: FollowNumbersDto){
    followNumbersDto.followerNum = followerNum
    followNumbersDto.followingNum = followingNum
}