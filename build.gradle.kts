plugins {
    java
    `maven-publish`
    signing
    id("pl.allegro.tech.build.axion-release") version "1.17.2"
    checkstyle
    jacoco
}

group = "pl.tfij"
project.version = scmVersion.version

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.puppycrawl.tools:checkstyle:10.17.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // https://github.com/google/guava/issues/6825
    modules {
        // replace old dependency `google-collections` with `guava`
        module("com.google.collections:google-collections") {
            replacedBy("com.google.guava:guava", "google-collections is part of guava")
        }
    }
    // -------------------------------------------

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}



tasks.getByName<Jar>("jar") {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("sonatype") {
            artifactId = "check-tfij-style"
            from(components.getByName("java"))
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult();
                }
            }
            pom {
                name.set("check-tfij-style")
                description.set("A set of additional check to use with checkstyle.")
                url.set("https://github.com/tfij/check-tfij-style")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("tfij")
                        name.set("Tomasz Fijakowski")
                        email.set("tomasz@chi.pl")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/tfij/check-tfij-style.git")
                    developerConnection.set("scm:git:https://github.com/tfij/check-tfij-style.git")
                    url.set("https://github.com/tfij/check-tfij-style.git")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

if (System.getenv().containsKey("SIGNING_GPG_KEY_ID")) {
    signing {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_GPG_KEY_ID"),
            System.getenv("SIGNING_GPG_PRIVATE_KEY"),
            System.getenv("SIGNING_GPG_PRIVATE_KEY_PASSWORD")
        )
        sign(publishing.publications.getByName("sonatype"))
    }
}

tasks.getByName<Javadoc>("javadoc") {
    if(JavaVersion.current().isJava9Compatible) {
        (options as? StandardJavadocDocletOptions)?.addBooleanOption("html5", true)
    }
}

checkstyle {
    toolVersion = "10.3.4"
    sourceSets = listOf(project.sourceSets.main.orNull)
}
