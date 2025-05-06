plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("com.google.gms.google-services")

    id ("kotlin-parcelize")

}

android {
    namespace = "badang.android.taskit"
    compileSdk = 35

    defaultConfig {
        applicationId = "badang.android.taskit"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true

    }

    /*composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }*/
}

dependencies {

    /*Navigation Fragment*/
    implementation("androidx.navigation:navigation-fragment:2.8.0")
    implementation("androidx.navigation:navigation-ui:2.8.0")

    /*Gson*/
    implementation("com.google.code.gson:gson:2.13.1")

    /*Room-Database*/
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    /*Splash Screen*/
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    /*Hilt-Dagger*/
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")

    /*Firebase*/

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.0"))

    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")

    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    //Kotlin coroutine ktx for Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

    //Kotlin - Coroutine with Worker
    implementation ("androidx.work:work-runtime-ktx:2.8.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}