package badang.android.taskit.feature_task.domain.use_case.task

import badang.android.taskit.feature_task.data.local.room.TaskEntity
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): TaskEntity? {
        return repository.getTask(id)
    }
}