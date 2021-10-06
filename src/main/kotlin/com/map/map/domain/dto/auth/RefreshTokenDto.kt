package com.map.map.domain.dto.auth

import javax.validation.constraints.NotBlank

class RefreshTokenDto {
    @NotBlank(message = "refreshToken null 불가능")
    val refreshToken: String? = null
}