package com.map.map.domain.dto.auth

import javax.validation.constraints.NotBlank

class RefreshTokenDto {
    @NotBlank
    val accessToken: String? = null

    @NotBlank
    val refreshToken: String? = null
}