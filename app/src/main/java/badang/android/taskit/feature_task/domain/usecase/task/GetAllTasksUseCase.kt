package badang.android.taskit.feature_task.domain.usecase.task

import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke() = repository.getAllTasks()
}