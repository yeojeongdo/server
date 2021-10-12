package com.map.map.domain.entity

import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "follower_idx"), Index(columnList = "following_idx")])
class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    @ManyToOne
    @JoinColumn(nullable = false)
    var follower:User? = null

    @ManyToOne
    @JoinColumn(nullable = false)
    var following:User? = null

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    var state: Boolean = false

    constructor(){

    }

    constructor(follower:User, following: User){
        this.follower = follower
        this.following = following
    }
}