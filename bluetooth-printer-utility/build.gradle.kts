plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.paragon.btprinter"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(files("libs/ZSDK_ANDROID_API.jar"))
}

afterEvaluate {
    android.libraryVariants.forEach { variant ->
        publishing.publications.create<MavenPublication>(variant.name) {
            groupId = "com.paragon"
            artifactId = "local-bt-printer"
            version = "1.0.3"

            from(components.findByName(variant.name))
        }
    }
}