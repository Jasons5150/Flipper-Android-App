package com.flipperdevices.settings.impl.fragments

import androidx.compose.runtime.Composable
import com.flipperdevices.core.ui.ComposeFragment
import com.flipperdevices.settings.impl.composable.ComposableSettings

class SettingsFragment : ComposeFragment() {
    @Composable
    override fun RenderView() {
        ComposableSettings()
    }
}