package badang.android.taskit.feature_task.domain.use_case.task

import badang.android.taskit.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteAllTasksUserCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(){
        return repository.deleteAllTasks()
    }
}