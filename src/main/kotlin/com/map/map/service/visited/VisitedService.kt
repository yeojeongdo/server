package com.map.map.service.visited

import com.map.map.domain.entity.Building
import com.map.map.domain.entity.User
import com.map.map.domain.entity.Visited

interface VisitedService {
    fun setVisited(building: Building, user: User): Visited
}