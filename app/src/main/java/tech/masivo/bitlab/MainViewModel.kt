package tech.masivo.bitlab

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.masivo.bitlab.domain.MainService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    service: MainService
) : ViewModel() {
    var name = service.name
}