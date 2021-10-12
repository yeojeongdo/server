package com.map.map.domain.entity

import javax.persistence.*

@Entity
class AlbumLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null

    @ManyToOne
    @JoinColumn
    var user: User? = null

    @ManyToOne
    @JoinColumn
    var album: Album? = null

    @Column(nullable = false)
    var isState: Boolean? = false

    constructor()

    constructor(user: User?, album: Album?, isState: Boolean?) {
        this.user = user
        this.album = album
        this.isState = isState
    }
}