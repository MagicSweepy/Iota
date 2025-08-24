import io.gitlab.gregtechlite.magicbookgradle.utils.deobf
import io.gitlab.gregtechlite.magicbookgradle.utils.modGroup

plugins {
    `maven-publish`
    id("io.gitlab.gregtechlite.magicbookgradle") version "1.0.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

magicbook {
    includeWellKnownRepositories()
    usesJUnit()
    java {
        enableModernJavaSyntax()
        withSourcesJar()
    }

    mod {
        generateGradleTokenClass = "$modGroup.api.IotaTags"
        enabledJava17RunTasks = true
        useAccessTransformers()
        usesMixin()
    }
}

minecraft {
    mcpMappingChannel.set("stable")
    mcpMappingVersion.set("39")
}

tasks {
    dependencies {
        compileOnlyApi(libs.annotations)
        annotationProcessor(libs.annotations)

        compileOnly(libs.lombok)
        annotationProcessor(libs.lombok)

        shadowImplementation(libs.streamEx)
        shadowImplementation(libs.joml)
    }
}