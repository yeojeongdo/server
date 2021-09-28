package com.map.map.domain.response.album

import com.map.map.domain.response.building.BuildingInfoRo
import com.map.map.domain.response.user.UserInfoRo
import java.util.*

class AlbumListRo {
    var id : Long? = null
    var photo: String? = null
    var user : UserInfoRo? = null
    var createDate: Date? = null
    var building: BuildingInfoRo? = null
}