package com.map.map.JpaTest

import com.map.map.domain.repository.CommentRepo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentJpaTest {
    @Autowired
    private var commentRepo : CommentRepo? = null

    @Test
    fun countNum(){
        var count = commentRepo!!.countCommentNum(22)
        println(count)
    }
}