import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

    implementation("org.springframework.boot:spring-boot-starter-security")     // spring security
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")                           // jwt
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")                             // jwt
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")                          // jwt

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
    testImplementation("org.springframework.security:spring-security-test")
}

// main class 설정
springBoot {
    mainClass.set("com.auth.AuthApplication")
}