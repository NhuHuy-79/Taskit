package badang.android.taskit.feature_task.domain.usecase.alarm

import badang.android.taskit.feature_task.domain.repository.alarm.AlarmScheduler
import badang.android.taskit.feature_task.domain.model.Task
import javax.inject.Inject

class CancelScheduleUseCase @Inject constructor(
    private val scheduler: AlarmScheduler
){
    operator fun invoke(task: Task){
        return scheduler.onSchedule(task)
    }
}