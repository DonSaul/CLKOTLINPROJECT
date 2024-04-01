import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("kapt") version "1.9.22"
}

group = "com.jobsearch"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation ("io.jsonwebtoken:jjwt:0.9.1")
	implementation("javax.servlet:javax.servlet-api:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.h2database:h2")

	//Handle data/time and serialization to JSON on testing
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.5")
	// JAXB API dependency
	implementation("javax.xml.bind:jaxb-api:2.3.1")

	// JAXB reference implementation (GlassFish)
	implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")

	// https://mvnrepository.com/artifact/com.itextpdf/itext7-core
	implementation("com.itextpdf:itext7-core:8.0.3")


	implementation ("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.glassfish.jersey.core:jersey-common:3.1.5")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("com.h2database:h2:2.2.224")


	kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")

	//coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.4")


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
