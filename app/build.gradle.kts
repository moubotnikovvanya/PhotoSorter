plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.photosorter"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.photosorter"
        minSdk = 26
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
    buildFeatures{
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {



    implementation("com.google.android.material:material:1.13.0")

    implementation("androidx.documentfile:documentfile:1.0.1")
    implementation("androidx.fragment:fragment-ktx:1.8.5")

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.7")//2.8.7
    implementation("androidx.navigation:navigation-ui-ktx:2.8.7")
    implementation("io.coil-kt:coil:2.7.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}