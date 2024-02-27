package dev.sergiobelda.todometer.common.domain.usecase.task

import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository

class ToggleTaskPinnedValueUseCase(
    private val taskRepository: ITaskRepository
) {

    suspend operator fun invoke(id: String) =
        taskRepository.toggleTaskPinnedValue(id)
}
