package com.map.map.domain.dto.building

import javax.validation.constraints.NotBlank

class GetBuildingsAlbumListDto {
    @NotBlank(message = "주소는 비어있을 수 없습니다.")
    var address: String? = null

    var lastAlbumId: Long? = null
}