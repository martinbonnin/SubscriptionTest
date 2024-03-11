plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.apollographql.apollo3")
    id("co.touchlab.skie")
}
apollo {
    service("service") {
        packageName.set("subscription.test")
        introspection {
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
            endpointUrl.set("https://leonidas-naiwjdzjsq-od.a.run.app/graphql")
        }
    }
}

kotlin {
    jvm()
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries {
            this.framework {
                baseName = "Shared"
                isStatic = true
            }
        }
    }

    sourceSets.getByName("commonMain").dependencies {
        api("com.apollographql.apollo3:apollo-runtime")
        api("com.apollographql.apollo3:apollo-websocket-network-transport-incubating")
    }
}