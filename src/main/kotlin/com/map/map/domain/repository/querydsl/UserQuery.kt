package com.map.map.domain.repository.querydsl

import com.map.map.domain.entity.QComment
import com.map.map.domain.entity.QFollow
import com.map.map.domain.entity.QFollow.follow
import com.map.map.domain.entity.QUser
import com.map.map.domain.entity.QUser.*
import com.map.map.domain.entity.User
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class UserQuery  @Autowired constructor(
    private var em : EntityManager,
    private var queryFactory: JPAQueryFactory
){
    fun getUserFollower(userIdx:Long, lastId:Long?): List<User>{
        return queryFactory
            .select(follow.follower)
            .from(follow)
            .join(follow.follower, user)
            .where(
                follow.following.idx.eq(userIdx),
                follow.state.eq(true),
                lessThanIdForGetFollower(lastId)
            )
            .limit(10)
            .orderBy(follow.follower.idx.desc())
            .fetch()
    }

    fun getUserAllFollower(userIdx: Long): List<User> {
        return queryFactory
            .select(follow.follower)
            .from(follow)
            .join(follow.follower, user)
            .orderBy(follow.follower.idx.desc())
            .fetch()
    }

    fun getUserFollowing(userIdx:Long, lastId:Long?): List<User>{
        return queryFactory
            .select(follow.following)
            .from(follow)
            .join(follow.following, user)
            .where(
                follow.follower.idx.eq(userIdx),
                follow.state.eq(true),
                lessThanIdForGetFollowing(lastId)
            )
            .limit(10)
            .orderBy(follow.following.idx.desc())
            .fetch()
    }

    fun lessThanIdForGetFollower(lastId: Long?): BooleanExpression?{
        return if(lastId != null){
            follow.follower.idx.lt(lastId)
        }else{
            null
        }
    }

    fun lessThanIdForGetFollowing(lastId: Long?): BooleanExpression?{
        return if(lastId != null){
            follow.following.idx.lt(lastId)
        }else{
            null
        }
    }
}