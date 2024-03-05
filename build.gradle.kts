import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"		apply false
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
	group = "com.mini"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

}

subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-jpa")

	tasks.register("prepareKotlinBuildScriptModel"){}

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.springframework.boot:spring-boot-starter-validation")

		implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-jdbc")
		runtimeOnly("com.mysql:mysql-connector-j")


		implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("io.jsonwebtoken:jjwt-api:0.12.3")
		runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
		runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.security:spring-security-test")
		testImplementation("io.kotest:kotest-assertions-core:5.8.0")
		testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
		testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
	}

}



