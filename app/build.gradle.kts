plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.psychoremstered"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.psychoremstered"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Image
    implementation(libs.coil.compose)

    // Room
    implementation(libs.room)
    kapt(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Gson
    implementation(libs.gson)

    // Test
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    // Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    // Flow testing
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Google Credential Manager auth
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)

    // Firebase
    implementation(platform(libs.firebase.bom))
    // Auth
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    // Database
    implementation(libs.firebase.database.ktx)
    // Storage
    implementation(libs.firebase.storage)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Google Pay
    implementation(libs.play.services.wallet)
    implementation(libs.compose.pay.button)

    // Window size
    implementation(libs.androidx.material3.window.size)

    // Animation
    implementation(libs.androidx.compose.animation)
}