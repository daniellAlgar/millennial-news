object ApplicationId {
    const val id = "com.algar.millennial_news"
}

object Modules {
    const val app = ":app"
    const val common = ":common"
    const val commonTest = ":common_test"
    const val model = ":data:model"
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
    const val retrofit = "2.6.0"
    const val retrofitGson = "2.4.0"
    const val okHttp = "3.12.1"
    const val gson = "2.8.5"
    const val mockWebServer = "2.7.5"
    const val mockk = "1.9.2"
    const val coroutines = "1.3.2"
    const val joda = "2.9.9"
    const val constraintLayout = "1.1.3"
    const val lifecycle = "2.1.0-alpha04"
    const val recyclerview = "1.0.0"
    const val nav = "2.0.0"
    const val fragmentTest = "1.1.0-alpha06"
    const val databinding = "3.3.2"
}

object Libraries {
    // Koin
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Time
    const val joda = "joda-time:joda-time:${Versions.joda}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object AndroidLibraries {
    // Kotlin
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
}

object TestLibraries {
    // Android Test
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val fragmentNav = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"

    // Mock
    const val mockWebServer = "com.squareup.okhttp:mockwebserver:${Versions.mockWebServer}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    // Coroutine
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // Koin
    const val koin = "org.koin:koin-test:${Versions.koin}"

    // Data binding
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.databinding}"
}