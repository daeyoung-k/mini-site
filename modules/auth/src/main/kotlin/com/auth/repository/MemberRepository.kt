package com.auth.repository

import com.auth.domain.Member
import com.auth.domain.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}

interface MemberRoleRepository: JpaRepository<MemberRole, Long>