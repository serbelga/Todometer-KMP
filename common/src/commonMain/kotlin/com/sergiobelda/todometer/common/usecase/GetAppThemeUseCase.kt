package com.sergiobelda.todometer.common.usecase

import com.sergiobelda.todometer.common.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetAppThemeUseCase(private val userPreferencesRepository: IUserPreferencesRepository) {

    /**
     * Retrieves the current selected AppTheme in user preferences
     * every time it changes.
     */
    operator fun invoke(): Flow<Int> = userPreferencesRepository.getUserTheme()
}
