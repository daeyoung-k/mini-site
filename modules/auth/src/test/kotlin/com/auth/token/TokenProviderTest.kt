package com.auth.token

import com.auth.authority.JwtTokenProvider
import com.auth.dto.MemberDtoRequest
import com.auth.dto.TokenInfo
import com.auth.entity.Member
import com.auth.entity.MemberRole
import com.auth.repository.MemberRepository
import com.auth.repository.MemberRoleRepository
import com.auth.status.ROLE
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.security.SignatureException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import jakarta.transaction.Transactional
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Propagation

private val logger = KotlinLogging.logger {}

@Transactional(Transactional.TxType.SUPPORTS)
@SpringBootTest
class TokenProviderTest(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
): DescribeSpec({
//    extensions(SpringExtension)
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    describe("JWT 토큰") {
        context(" 토큰 생성") {
            val memberData: Member = MemberDtoRequest("test@test.com", "test", "1234test!@").toEntity()
            memberRepository.save(memberData)

            val memberRole = MemberRole(null, ROLE.MEMBER, memberData)
            memberRoleRepository.save(memberRole)

            val member: Member = memberRepository.findByEmail(memberData.email)!!

            it("토큰이 반환된다") {
                val tokenInfo: TokenInfo = jwtTokenProvider.createToken(member)
                println(tokenInfo.accessToken)
                tokenInfo shouldNotBe null
                tokenInfo.accessToken shouldNotBe null
            }
        }

    }

    describe("토큰 정보 추출") {
        context("정상적인 토큰이 들어왔을때") {
            val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2R1ZDQ5NzZAbmF2ZXIuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJpYXQiOjE3MDc4MDg2MTAsImV4cCI6MTcwNzgxMDQxMH0.ORs1Ew1qearxZ1I4sDTdSagNszRNGdW0FRfqZND6m1sGSaGxmZKwGwO8D0ilFQDbc9tsmmRTO2wwW1UIoBOBug"

            it("정상적으로 토큰 정보를 추출한다") {
                val result: Authentication = jwtTokenProvider.getAuthentication(token)
                result.shouldBeInstanceOf<Authentication>()
            }
        }
        context("비정상적인 토큰이 들어왔을때") {
            val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2R1ZDQ5NzZAbmF2ZXIuY29tIiwiYXV0aCI6IlJPTEVfTUSIiwiaWF0IjoxNzA3MjY3MTk4LCJleHAiOjE3MDczNTM1OTh9.AoNKL0gO0e47VsSwO6pPWWUzXHvrjvGxUA2ZOyNfP5d9zpMGJalBURSAO4uu85Z599NmSjGfbcbT1hyrFb6Tiw"

            it("예외를 발생시킨다") {
                shouldThrow<SecurityException> {
                    jwtTokenProvider.getAuthentication(token)
                }
            }
        }
    }
})