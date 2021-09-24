package com.map.map.domain.dto.album

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class PostAlbumDto {
    @NotNull(message = "address null 불가능")
    var address: String? = null

    @NotNull(message = "latitude null 불가능")
    var latitude: Double? = null

    @NotNull(message = "longitude null 불가능")
    var longitude: Double? = null

    @NotNull(message = "memo null 불가능")
    var  memo : String? = null

    @NotNull(message = "사진 null 불가능")
    @Size(min = 1, message = "사진은 1장 이상 올려야 합니다.")
    var files: List<MultipartFile> = mutableListOf()
}