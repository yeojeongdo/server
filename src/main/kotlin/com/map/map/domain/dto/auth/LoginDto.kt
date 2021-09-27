package com.map.map.domain.dto.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


class LoginDto {
    @NotBlank(message = "id null 불가능")
    var id:String? = null
    @NotBlank(message = "password null 불가능")
    var password: String? = null

}