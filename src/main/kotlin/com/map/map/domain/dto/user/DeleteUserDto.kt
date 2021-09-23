package com.map.map.domain.dto.user

import javax.validation.constraints.NotNull

class DeleteUserDto {
    @NotNull(message = "name null 불가능")
    var password : String? = null
}