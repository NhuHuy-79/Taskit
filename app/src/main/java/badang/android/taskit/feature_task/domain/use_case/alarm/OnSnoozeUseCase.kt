package badang.android.taskit.feature_task.domain.use_case.alarm

import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import javax.inject.Inject

class OnSnoozeUseCase @Inject constructor(private val scheduler: AlarmScheduler) {
    operator fun invoke(task: Task){
        return scheduler.onSnooze(task)
    }
}