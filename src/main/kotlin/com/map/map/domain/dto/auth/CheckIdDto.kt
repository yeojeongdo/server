package com.map.map.domain.dto.auth;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


class CheckIdDto {
    @NotNull(message = "id null 불가능")
    @Length(min = 4, max = 20, message = "id 길이 4~20")
    var id: String? = null
}
