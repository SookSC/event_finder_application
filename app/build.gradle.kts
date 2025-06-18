import java.util.Properties

val secretsFile = rootProject.file("secrets.properties")
val properties = Properties().apply {
    if (secretsFile.exists()) {
        load(secretsFile.inputStream())
    }
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val predictHQApiKey = properties["PREDICTHQ_API_KEY"] ?: "MISSING_API_KEY"
val ticketMasterApiKey = properties["TICKETMASTER_API_KEY"] ?: "MISSING_API_KEY"

android {
    namespace = "com.sookeongcho.eventHub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sookeongcho.eventHub"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "PREDICTHQ_API_KEY", "\"${properties["PREDICTHQ_API_KEY"] ?: ""}\"")
        buildConfigField("String", "TICKETMASTER_API_KEY", "\"${properties["TICKETMASTER_API_KEY"] ?: ""}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout.v221)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.glide)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Location access
    implementation(libs.play.services.location)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}