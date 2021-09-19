package com.sergiobelda.todometer.ui

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.common.usecase.GetAppThemeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    getAppThemeUseCase: GetAppThemeUseCase
) : ViewModel() {

    val appTheme: StateFlow<Int> =
        getAppThemeUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            MODE_NIGHT_FOLLOW_SYSTEM
        )
}
