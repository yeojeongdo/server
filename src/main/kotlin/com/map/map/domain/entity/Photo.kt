package com.map.map.domain.entity

import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "idx")])
class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    @Column(nullable = false)
    var filed: String? = null
}