package com.map.map.domain.response.auth

data class LoginRo (
    var accessToken: String? = null,
    var refreshToken: String? = null
    )