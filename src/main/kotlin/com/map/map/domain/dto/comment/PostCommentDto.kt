package com.map.map.domain.dto.comment

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PostCommentDto {
    @NotBlank(message = "comment는 null이 불가능 합니다.")
    var comment : String? = null

    @NotNull(message = "앨범의 id는 null이 불가능 합니다.")
    var id: Long? = null
}