package com.map.map.domain.repository

import com.map.map.domain.entity.Building
import org.springframework.data.jpa.repository.JpaRepository
import java.net.Inet4Address

interface BuildingRepo : JpaRepository<Building, Long> {
    fun findByAddress(address: String) : Building?
}