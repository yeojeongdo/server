package com.map.map.domain.repository

import com.map.map.domain.entity.Visited
import org.springframework.data.jpa.repository.JpaRepository

interface VisitedRepo : JpaRepository<Visited, Long> {
}