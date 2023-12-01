package tech.masivo.bitlab

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.masivo.bitlab.domain.MainService
import tech.masivo.bitlab.ui.MainUiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @Suppress("UNUSED_PARAMETER") service: MainService
) : ViewModel() {
    val uiState = MainUiState()
}