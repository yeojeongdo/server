package com.map.map.domain.entity

import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "address")])
class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var address: String? = null

    //위도
    @Column(nullable = false)
    var latitude: Double? = null

    //경도
    @Column(nullable = false)
    var longitude: Double? = null

    @OneToMany(mappedBy = "building")
    var visitor: MutableList<Visited> = mutableListOf()

    @OneToMany(mappedBy = "building")
    var albums: MutableList<Album> = mutableListOf()
}