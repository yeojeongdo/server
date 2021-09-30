package com.map.map.domain.response.album

import com.map.map.domain.response.building.BuildingInfoRo
import com.map.map.domain.response.user.UserInfoRo
import java.util.*

class AlbumDetailRo {
    var id : Long? = null
    var photo: MutableList<String> = mutableListOf()
    var memo : String? = null
    var user : UserInfoRo? = null
    var createDate: Date? = null
    var building: BuildingInfoRo? = null
    var commentNum: Long = 0
    var likeNum: Long = 0
}