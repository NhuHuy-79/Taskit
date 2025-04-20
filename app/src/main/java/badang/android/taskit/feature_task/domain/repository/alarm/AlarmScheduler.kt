package badang.android.taskit.feature_task.domain.repository.alarm

import badang.android.taskit.feature_task.domain.model.Task

interface AlarmScheduler {
    fun onSchedule(task: Task)
    fun cancelSchedule(task: Task)
    fun onSnooze(task:Task)
}