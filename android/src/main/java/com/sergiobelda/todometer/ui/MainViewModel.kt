package com.sergiobelda.todometer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.common.preferences.AppTheme
import com.sergiobelda.todometer.common.usecase.GetAppThemeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    getAppThemeUseCase: GetAppThemeUseCase
) : ViewModel() {

    val appTheme: StateFlow<AppTheme> =
        getAppThemeUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            AppTheme.FOLLOW_SYSTEM
        )
}
