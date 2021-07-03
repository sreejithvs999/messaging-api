import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
   application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

   // implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
   // implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.opencsv:opencsv:5.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}


tasks.withType<Jar>() {
    manifest.attributes["Main-Class"]="com.hotels.datatools.MainKt"
    from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
}

application {
    mainClass.set("com.hotels.datatools.MainKt")
}

