package com.map.map.service.follow

import com.map.map.domain.entity.Follow
import com.map.map.domain.repository.FollowRepo
import com.map.map.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowServiceImpl @Autowired constructor(
    private var followRepo: FollowRepo,
    private var userService: UserService
) : FollowService{

    @Transactional
    override fun changeFollowState(userId: String, userIdx: Long) {
        var me = userService.getUser(userId)
        var targetUser = userService.getUser(userIdx)

        var follow = followRepo.findByFollowerAndFollowing(me, targetUser) ?: Follow(me, targetUser);
        follow.state = !follow.state

        followRepo.save(follow)
    }
}