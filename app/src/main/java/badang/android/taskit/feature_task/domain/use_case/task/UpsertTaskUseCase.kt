package badang.android.taskit.feature_task.domain.use_case.task

import badang.android.taskit.feature_task.data.local.room.TaskEntity
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toDomain
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class UpsertTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val scheduler: AlarmScheduler
) {
    suspend operator fun invoke(task: TaskEntity) {
        val oldTask = repository.getTask(task.id)

        repository.upsertTask(task)

        if (oldTask != null) {
            if (oldTask.time != task.time){
                scheduler.cancelSchedule(oldTask.toDomain())
                scheduler.onSchedule(task.toDomain())
            }
        } else {
            scheduler.onSchedule(task.toDomain())
        }

    }
}