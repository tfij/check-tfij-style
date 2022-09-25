plugins {
    java
    id("pl.allegro.tech.build.axion-release") version "1.14.1"
}

group = "pl.tfij"
scmVersion {
    versionCreator("versionWithBranch")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.puppycrawl.tools:checkstyle:8.38")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}