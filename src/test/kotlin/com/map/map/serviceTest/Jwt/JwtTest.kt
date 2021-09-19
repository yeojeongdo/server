package com.map.map.serviceTest.Jwt

import com.map.map.enum.JwtType
import com.map.map.service.jwt.JwtServiceImpl
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtTest {

    @Autowired
    private val jwtServiceImpl: JwtServiceImpl = JwtServiceImpl()

    private val log = LoggerFactory.getLogger(JwtTest::class.java)

    /**
     * 토큰 생성 테스트
     */
    @Test
    fun createJwtToken(){
        val id = "qwer1234"
        val authType = JwtType.ACCESS

        val token = jwtServiceImpl.createToken(id, authType)

        log.info(token)
    }
}