package com.sergiobelda.todometer.ui.editproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.common.data.Result
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.UpdateProjectUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditProjectViewModel(
    getProjectSelectedUseCase: GetProjectSelectedUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase
) : ViewModel() {

    val projectSelected: StateFlow<Result<Project>> =
        getProjectSelectedUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    fun updateProject(project: Project) = viewModelScope.launch {
        updateProjectUseCase(project)
    }
}
