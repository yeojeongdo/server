package com.map.map.domain.response.user

import com.map.map.enum.Gender
import java.util.*

class UserInfoRo {
    var id: Long? = null
    var name: String? = null
    var image: String? = null
    var gender: Gender? = null
    var birthDay: Date? = null
}