package com.sergiobelda.todometer.common.usecase

import com.sergiobelda.todometer.common.repository.IUserPreferencesRepository

class SetAppThemeUseCase(private val userPreferencesRepository: IUserPreferencesRepository) {

    /**
     * Updates the current selected AppTheme.
     */
    suspend operator fun invoke(theme: Int) =
        userPreferencesRepository.setUserTheme(theme)
}
