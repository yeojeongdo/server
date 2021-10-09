package com.map.map.domain.response.comment

import com.map.map.domain.response.user.UserInfoRo

class CommentRo {
    var id : Long? = null
    var content: String? = null
    var user : UserInfoRo? = null
}