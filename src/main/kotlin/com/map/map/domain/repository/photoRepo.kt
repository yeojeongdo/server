package com.map.map.domain.repository

import com.map.map.domain.entity.Photo
import org.springframework.data.jpa.repository.JpaRepository

interface photoRepo : JpaRepository<Photo, Long> {
}