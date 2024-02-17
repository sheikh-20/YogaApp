plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    kotlin("kapt")
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.android.kotlin.serialize)
    alias(libs.plugins.google.service)
}

android {
    namespace = "com.bitvolper.yogazzz"
    compileSdk = 34

    signingConfigs {
        create("config") {
            keyAlias = "yoga"
            keyPassword = "Sheikh"
            storeFile = file("/media/sheikh/hdd/AndroidStudioProjects/AndroidStudioProjects/YogaApp/app/keystore.jks")
            storePassword = "Sheikh"
            enableV4Signing = true
        }
    }

    defaultConfig {
        applicationId = "com.bitvolper.yogazzz"
        minSdk = 21
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            resValue(type = "string",name = "app_name", value = "YogazzZ_Debug")
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName("release") {
            resValue(type = "string",name = "app_name", value = "YogazzZ")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("config")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.play.service)
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.test.manifest)


    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // ViewModel with ktx
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Material icon extended
    implementation(libs.material.icon.extended)

    // Dagger - Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.nav.compose)

    // Pager
    implementation(libs.pager.indicator)
    implementation(libs.pager)

    // Timber for log
    implementation(libs.timber)

    // Number picker
    implementation(libs.number.picker)

    // Compose Chart
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)

    // Play App Update
    implementation(libs.play.app.update)
    implementation(libs.play.app.update.ktx)

    // Gson
    implementation(libs.gson)
}