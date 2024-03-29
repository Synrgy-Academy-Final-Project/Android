plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.synrgyacademy.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.synrgyacademy.finalproject"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")

    // splash core
    implementation("androidx.core:core-splashscreen:1.0.1")

    // navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation("androidx.hilt:hilt-navigation:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    // prefs
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.test.espresso:espresso-intents:3.5.1")

    // otp view
    implementation("com.github.aabhasr1:OtpView:v1.1.2-ktx")

    // range seekbar
    implementation("com.github.Jay-Goo:RangeSeekBar:v3.0.0")

    // circle image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // pdf viewer
    implementation("com.github.afreakyelf:Pdf-Viewer:v2.0.5")
}
kapt {
    correctErrorTypes = true
}