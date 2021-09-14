package com.map.map.service.auth

import com.map.map.domain.dto.auth.RegisterDto


interface AuthService {
    fun register(registerDto: RegisterDto)
    fun checkId(id: String)
}