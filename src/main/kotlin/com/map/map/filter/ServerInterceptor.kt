package com.map.map.filter

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ServerInterceptor : HandlerInterceptor {

    @Value("\${server.port}")
    private val port: Int? = null

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val serverAddress = "${request.localAddr}:${port}"

        request.setAttribute("serverAddress", serverAddress)

        return true
    }
}