plugins {
    id("flipper.android-compose")
    id("flipper.anvil")
}

android.namespace = "com.flipperdevices.wearable.sync.wear.impl"

dependencies {
    implementation(projects.components.wearable.sync.common)
    implementation(projects.components.wearable.sync.wear.api)

    implementation(projects.components.core.di)
    implementation(projects.components.core.activityholder)
    implementation(projects.components.core.log)
    implementation(projects.components.core.ktx)
    implementation(projects.components.core.ui.res)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.navigation)

    implementation(projects.components.bridge.dao.api)
    implementation(projects.components.wearable.emulate.wear.api)
    implementation(projects.components.wearable.core.ui.components)

    implementation(libs.wear)
    implementation(libs.wear.gms)
    implementation(libs.wear.interaction.phone)
    implementation(libs.wear.interaction.remote)

    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.play.services)
    implementation(libs.kotlin.immutable.collections)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.wear.foundation)
    implementation(libs.compose.wear.material)
    implementation(libs.compose.wear.navigation)
    implementation(libs.horologist.layout)
    implementation(libs.lifecycle.compose)

    // Dagger deps
    implementation(libs.tangle.viewmodel.compose)
    implementation(libs.tangle.viewmodel.api)
    anvil(libs.tangle.viewmodel.compiler)
}
