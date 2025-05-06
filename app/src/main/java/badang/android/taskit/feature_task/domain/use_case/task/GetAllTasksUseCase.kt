package badang.android.taskit.feature_task.domain.use_case.task

import badang.android.taskit.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke() = repository.getAllTasks()
}