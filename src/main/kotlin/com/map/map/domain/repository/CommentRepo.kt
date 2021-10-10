package com.map.map.domain.repository

import com.map.map.domain.entity.Comment
import com.map.map.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepo : JpaRepository<Comment, Long> {
    @Query(value = "select count(*) from comment where album_idx = :id", nativeQuery = true)
    fun countCommentNum(@Param("id") id:Long):Long

    fun deleteByIdxAndUser(idx:Long, user: User)
}