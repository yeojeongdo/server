package com.map.map.domain.dto.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

class PatchUserNameDto {
    @NotNull(message = "name null 불가능")
    @Length(min=2,max=20, message="name 길이 2~20")
    var name : String? = null
}