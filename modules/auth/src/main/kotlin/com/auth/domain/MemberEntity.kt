package com.auth.domain

import com.auth.status.ROLE
import com.auth.status.jwt.ProviderType
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, length = 100, updatable = false)
    val email: String,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(nullable = true, length = 100)
    val nickName: String? = null,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    val provider: ProviderType? = null,

    @Column(nullable = false)
    val createAt: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = true)
    val updateAt: LocalDateTime? = null,


    ) {
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val roles: List<MemberRole>? = null
}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member
)