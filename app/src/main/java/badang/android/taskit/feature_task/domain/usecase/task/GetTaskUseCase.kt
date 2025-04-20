package badang.android.taskit.feature_task.domain.usecase.task

import badang.android.taskit.feature_task.data.local.TaskEntity
import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): TaskEntity {
        return repository.getTask(id)
    }
}