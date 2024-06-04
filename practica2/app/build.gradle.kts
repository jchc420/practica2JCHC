plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jchc.practica2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jchc.practica2"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //Para retrofit y Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")

    //Adicional para el interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Glide y Picasso
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("com.squareup.picasso:picasso:2.71828")

    //Para las corrutinas
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //Imágenes con bordes redondeados
    implementation("com.makeramen:roundedimageview:2.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Para incluir videos con Youtube Player API
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
}