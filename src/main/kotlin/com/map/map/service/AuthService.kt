package com.map.map.service

import com.map.map.domain.dto.auth.RegisterDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


interface AuthService {
    fun register(registerDto: RegisterDto)
}