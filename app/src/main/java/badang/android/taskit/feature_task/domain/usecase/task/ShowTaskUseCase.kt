package badang.android.taskit.feature_task.domain.usecase.task

import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import javax.inject.Inject

class ShowTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: String){
        repository.showTask(id)
    }
}