package com.map.map.filter

import com.map.map.domain.entity.User
import com.map.map.enum.JwtType
import com.map.map.lib.AuthorizationExtractor
import com.map.map.service.jwt.JwtServiceImpl
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationInterceptor @Autowired constructor(
    private var jwtServiceImpl: JwtServiceImpl
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any) :Boolean{
        try {
            val token: String = AuthorizationExtractor.extract(request, "Bearer")

            // cors
            if (request.method != "OPTIONS") {
                if (token == null) {
                    throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
                }

                val claims: Claims = jwtServiceImpl.validateToken(token, JwtType.ACCESS)
                val userId: String? = claims["id"] as String?

                request.setAttribute("userId", userId)
            }

            return true
        } catch (e: Exception) {
            throw e;
        }
    }
}