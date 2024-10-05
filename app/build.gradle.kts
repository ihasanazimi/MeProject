plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.devtools.ksp")
    jacoco
}

android {

    namespace = "ir.ha.meproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.ha.meproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }


// Iterate over all application variants (e.g., debug, release)
    applicationVariants.all {
        val variantName = name.capitalize()

        // Define task names for unit tests and Android tests
        val unitTests = "test${variantName}UnitTest"
        val androidTests = "connected${variantName}AndroidTest"

        // Register a JacocoReport task for code coverage analysis
        tasks.register<JacocoReport>("Jacoco${variantName}CodeCoverage") {
            dependsOn(unitTests, androidTests)

            group = "Reporting"
            description = "Execute UI and unit tests, generate and combine Jacoco coverage report"

            reports {
                xml.required.set(true)
                html.required.set(true)
            }

            sourceDirectories.setFrom(files("$projectDir/src/main/java"))
            classDirectories.setFrom(
                files(
                    fileTree(mapOf("dir" to "$buildDir/intermediates/javac/${variantName}", "exclude" to exclusions)),
                    fileTree(mapOf("dir" to "$buildDir/tmp/kotlin-classes/${variantName}", "exclude" to exclusions))
                )
            )

            executionData.setFrom(
                fileTree(buildDir) {
                    include("**/*.exec", "**/*.ec")
                }
            )
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true

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
        viewBinding = true
    }




//    kapt {
//        correctErrorTypes = true
//    }

//    /** for pdf generator */
//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//    }

    packaging  {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
        }
    }

}

val exclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*"
)




tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

dependencies {

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("org.jetbrains.kotlin:kotlin-test")


    // base
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.core.animation)
    implementation(libs.androidx.legacy.support.v4)

    // Data Binding Runtime
    implementation(libs.androidx.databinding.runtime)

    // Google MaterialDesign
    implementation(libs.material)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Architecture lifecycles
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    implementation(libs.viewmodel)
    implementation(libs.androidx.lifecycle.extensions)

    /*  Dagger Hilt  */
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Glide ImageLoader
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    //Event Bus
    /* implementation("org.greenrobot:eventbus:3.3.1") */

    // room dataBase
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    // Nav Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Circle Image View
    //implementation("de.hdodenhof:circleimageview:3.1.0")

    // lottie animations
    implementation(libs.lottie)

    // SharedPreferences (DataStore)
    implementation(libs.androidx.datastore.preferences)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Pagination
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    testImplementation(libs.androidx.paging.common.ktx)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)

    // Multidex
    //implementation("com.android.support:multidex:1.0.3")

    // PDF View
    //implementation("com.dmitryborodin:pdfview-android:1.1.0")

    // persian datePicker
    //implementation("com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")

    // screen shot
    //implementation("com.github.nisrulz:screenshott:2.0.0")

    // Biometric (Fingerprint)
    implementation(libs.androidx.biometric)

    // GoogleServices (location)
    implementation(libs.play.services.location)

    // number picker btn style
    //implementation("it.sephiroth.android.library:number-sliding-picker:1.1.1")

    // GoogleServices (fireBase)
    //implementation("com.google.firebase:firebase-messaging:23.4.1")

    // Retrofit by converters
    implementation (libs.retrofit) // okHttp
    implementation (libs.converter.gson) // GSON Converter
    implementation (libs.converter.jackson) // Jackson Converter
    implementation (libs.adapter.rxjava3) // RX3 adapter
    /* implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0") */ // RX2 adapter

    // GSON
    implementation(libs.gson)

    // RxJava/RxAndroid Version 3
    implementation (libs.rxjava)
    implementation (libs.rxkotlin)
    implementation (libs.rxandroid)

    // RxJava/RxAndroid Version 2
    /* implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")
    */


    // Koin
    //val koinVersion= "3.5.0"
    //implementation("io.insert-koin:koin-android:$koinVersion")

    // Fresco
    //implementation("com.facebook.fresco:fresco:2.6.0")

    // Picasso
    // implementation("com.squareup.picasso:picasso:2.71828")

    // Coil ImageLoader
    //implementation("io.coil-kt:coil:2.0.0-rc03")


    // OkHttp Log
    //implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // chucker
    releaseImplementation (libs.chucker2)
    debugImplementation (libs.chucker1)

    //stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)
    implementation(libs.stetho.js.rhino)


    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.mockito:mockito-inline:4.5.1")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")


    // mockk
    val mockk = "1.13.10"
    testImplementation("io.mockk:mockk:$mockk")
    androidTestImplementation("io.mockk:mockk-android:$mockk")



    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // or the latest version
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0") // for running tests


    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")







}