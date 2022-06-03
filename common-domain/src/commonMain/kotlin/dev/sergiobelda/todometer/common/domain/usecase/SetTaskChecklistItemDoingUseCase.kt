package dev.sergiobelda.todometer.common.domain.usecase

import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.common.domain.repository.ITaskChecklistItemsRepository

class SetTaskChecklistItemDoingUseCase(
    private val taskChecklistItemsRepository: ITaskChecklistItemsRepository
) {

    suspend operator fun invoke(id: String) =
        taskChecklistItemsRepository.updateTaskChecklistItemState(id, TaskChecklistItemState.DOING)
}
