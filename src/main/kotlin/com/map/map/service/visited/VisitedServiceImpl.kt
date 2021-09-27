package com.map.map.service.visited

import com.map.map.domain.entity.Building
import com.map.map.domain.entity.User
import com.map.map.domain.entity.Visited
import com.map.map.domain.repository.VisitedRepo
import com.map.map.service.album.userAndBuildingToVisited
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VisitedServiceImpl @Autowired constructor(
    private var visitedRepo: VisitedRepo
): VisitedService{

    /**
     * 방문 세팅
     */
    override fun setVisited(building: Building, user: User): Visited {
        val visited = Visited()
        userAndBuildingToVisited(building, user, visited)

        return visited
    }



}