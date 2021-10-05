package com.map.map.service.jwt

import com.map.map.domain.response.auth.LoginRo
import com.map.map.enum.JwtType
import io.jsonwebtoken.Claims

interface JwtService {
    fun createToken(id: String, authType: JwtType): String
    fun validateToken(token: String, authType: JwtType): Claims
    fun refreshToken(refreshToken: String): LoginRo
}