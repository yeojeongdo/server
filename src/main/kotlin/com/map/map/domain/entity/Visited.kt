package com.map.map.domain.entity

import java.util.*
import javax.persistence.*

@Entity
class Visited {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_idx", nullable = false)
    var user: User? = null

    @ManyToOne
    @JoinColumn(name = "building_idx", nullable = false)
    var building: Building? = null

    @Column(nullable = false)
    var date: Date? = Date()


    fun add(user: User, building: Building){
        this.user = user
        user.visited.add(this)
        this.building = building
        building.visitor.add(this)
    }
}