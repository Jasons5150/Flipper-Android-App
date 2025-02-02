package com.flipperdevices.wearable.emulate.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flipperdevices.bridge.dao.api.model.FlipperKeyType
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.wearable.emulate.api.ChannelClientHelper
import com.flipperdevices.wearable.emulate.impl.api.EMULATE_PATH_KEY
import com.flipperdevices.wearable.emulate.impl.helper.ConnectionHelper
import com.flipperdevices.wearable.emulate.impl.helper.EmulateHelper
import com.flipperdevices.wearable.emulate.impl.helper.FlipperStatusHelper
import com.flipperdevices.wearable.emulate.impl.helper.WearStateMachineHelper
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import tangle.inject.TangleParam
import tangle.viewmodel.VMInject
import java.io.File

class WearEmulateViewModel @VMInject constructor(
    @TangleParam(EMULATE_PATH_KEY)
    private val keyPath: String,
    private val wearStateMachineHelper: WearStateMachineHelper,
    channelClientHelper: ChannelClientHelper,
    connectionHelper: ConnectionHelper,
    flipperStatusHelper: FlipperStatusHelper,
    private val emulateHelper: EmulateHelper
) : ViewModel(),
    LogTagProvider {
    override val TAG = "WearEmulateViewModel"

    private val keyToEmulate = KeyToEmulate(keyPath)

    init {
        combine(
            channelClientHelper.getState(),
            connectionHelper.getState(),
            flipperStatusHelper.getState(),
            emulateHelper.getState()
        ) { channelState, connectionState, flipperState, emulateState ->
            wearStateMachineHelper.onStatesUpdated(
                channelState,
                connectionState,
                flipperState,
                emulateState,
                keyToEmulate
            )
        }.launchIn(viewModelScope)
    }

    fun getWearEmulateState() = wearStateMachineHelper.getState()

    fun onClickEmulate() = emulateHelper.onClickEmulate(keyToEmulate)

    fun onShortEmulate() = emulateHelper.onShortEmulate(keyToEmulate)

    fun onStopEmulate() = emulateHelper.onStopEmulate()

    override fun onCleared() {
        super.onCleared()
        emulateHelper.onStopEmulate()
    }
}

enum class WearLoadingState {
    INITIALIZING,
    FINDING_PHONE,
    NOT_FOUND_PHONE,
    CONNECTING_PHONE,
    TEST_CONNECTION,
    CONNECTING_FLIPPER
}

data class KeyToEmulate(
    val keyPath: String
) {
    val keyType: FlipperKeyType?
        get() = FlipperKeyType.getByExtension(File(keyPath).extension)
}
