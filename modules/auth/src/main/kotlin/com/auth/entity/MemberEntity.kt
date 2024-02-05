package com.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, length = 100, updatable = false)
    val email: String,

    @Column(nullable = true, length = 100)
    val nickName: String?,

    @Column(nullable = false)
    val createAt: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = true)
    val updateAt: LocalDateTime? = null

)