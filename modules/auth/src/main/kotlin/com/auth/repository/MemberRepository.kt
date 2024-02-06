package com.auth.repository

import com.auth.entity.Member
import com.auth.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}

interface MemberRoleRepository: JpaRepository<MemberRole, Long>