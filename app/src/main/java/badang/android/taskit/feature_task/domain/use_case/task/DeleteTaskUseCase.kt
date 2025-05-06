package badang.android.taskit.feature_task.domain.use_case.task

import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val scheduler: AlarmScheduler
) {
    suspend operator fun invoke(task: Task){
         repository.deleteTask(task.id!!)
         scheduler.cancelSchedule(task)
    }
}