package dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem

import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.common.domain.repository.ITaskChecklistItemsRepository

class SetTaskChecklistItemUncheckedUseCase(
    private val taskChecklistItemsRepository: ITaskChecklistItemsRepository
) {

    suspend operator fun invoke(id: String) =
        taskChecklistItemsRepository.updateTaskChecklistItemState(id, TaskChecklistItemState.UNCHECKED)
}
