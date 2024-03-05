package com.common.dao.member

import com.common.domain.member.Member
import com.common.domain.member.MemberRole
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}

interface MemberRoleRepository: JpaRepository<MemberRole, Long>