package com.auth.member

import com.auth.authority.JwtTokenProvider
import com.auth.dto.MemberDtoRequest
import com.auth.domain.Member
import com.auth.domain.MemberRole
import com.auth.repository.MemberRepository
import com.auth.repository.MemberRoleRepository
import com.auth.status.ROLE
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode


@Transactional
@SpringBootTest
class ManualAuthorizationTest(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
): DescribeSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    val logger = KotlinLogging.logger {}

    describe("수동 인증") {
        context("회원가입") {
            val memberData: Member = MemberDtoRequest("test@test.com", "test", "test1234!@").toEntity()
            memberRepository.save(memberData)

            MemberRole(null, ROLE.MEMBER, memberData).let {
                memberRoleRepository.save(it)
            }

            val member: Member? = memberRepository.findByEmail(memberData.email)

            it("회원정보가 정상적으로 조회된다.") {
                member shouldNotBe null
                member?.email shouldBe "test@test.com"
            }

            it("토큰이 정상 발급된다.") {
                val token: String = jwtTokenProvider.createToken(member!!)
                token shouldNotBe null
                token.startsWith("Bearer") shouldBe true
            }
        }

    }
})