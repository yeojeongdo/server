package com.map.map.service.jwt

import com.map.map.domain.response.auth.UserTokenRes
import com.map.map.enum.JwtType

interface JwtService {
    fun createToken(id: String, authType: JwtType): String
    fun validateToken(token: String?): String?
    fun refreshToken(refreshToken: String?, accessToken: String?): UserTokenRes
}