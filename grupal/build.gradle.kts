plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.programacion.avanzada"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.jnosql.databases:jnosql-redis:1.1.1")

    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
    implementation("io.smallrye:jandex:3.5.1")

    implementation("org.hibernate.validator:hibernate-validator:8.0.3.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")

    implementation("org.glassfish:jakarta.json:2.0.1")

    // ver si eliminar
    implementation("org.eclipse.microprofile.config:microprofile-config-api:3.1")
    implementation("io.smallrye.config:smallrye-config:3.5.1")

    implementation("jakarta.json.bind:jakarta.json.bind-api:3.0.0")
    implementation("org.eclipse:yasson:3.0.3")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        output.setResourcesDir(output.classesDirs.files.first())
    }
}