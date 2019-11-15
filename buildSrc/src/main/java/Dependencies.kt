object ApplicationId {
    const val id = "com.algar.millennial_news"
}

object Modules {
    const val app = ":app"
    const val common = ":common"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val compileSdk = 28
    const val minSdk = 23
    const val targetSdk = 28
    const val kotlin = "1.3.50"
    const val gradle = "3.5.2"
    const val appCompat = "1.1.0"
    const val coreKtx = "1.1.0"
    const val androidJunit = "1.1.1"
    const val androidTestRunner = "1.1.2-alpha02"
    const val espressoCore = "3.2.0-alpha02"
    const val archCoreTest = "2.0.0"
    const val koin = "1.0.2"
}

object Libraries {
    // Koin
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
}

object TestLibraries {
    // Android Test
    val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
}