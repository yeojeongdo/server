package com.map.map.domain.dto.like

import javax.validation.constraints.NotEmpty

class LikeDto {
    @NotEmpty(message = "albumId 빔")
    val albumId: Long? = null
}