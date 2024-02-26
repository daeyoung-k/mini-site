import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")     // spring security
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")                           // jwt
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")                             // jwt
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")                          // jwt

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

// main class 설정
springBoot {
    mainClass.set("com.auth.AuthApplication")
}