import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":common"))
}

// main class 설정
springBoot {
    mainClass.set("com.auth.AuthApplication")
}