package badang.android.taskit.feature_task.domain.usecase.task

import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import javax.inject.Inject

class ToggleCompletingTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: String){
        return repository.toggleCompletingTask(id)
    }
}