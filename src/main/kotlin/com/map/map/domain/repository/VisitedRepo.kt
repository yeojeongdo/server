package com.map.map.domain.repository

import com.map.map.domain.entity.Building
import com.map.map.domain.entity.User
import com.map.map.domain.entity.Visited
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VisitedRepo : JpaRepository<Visited, Long> {
    fun findByBuildingAndUser(building: Building, user: User) : Optional<Visited>
}