import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("maven-publish")
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.illenko.payment"
version = "0.0.5-SNAPSHOT"

val kotlinLoggingVersion: String by project

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenLocal()
	mavenCentral()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
			artifact(tasks.kotlinSourcesJar)
		}
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
