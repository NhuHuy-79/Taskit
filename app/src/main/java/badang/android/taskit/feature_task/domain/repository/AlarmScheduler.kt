package badang.android.taskit.feature_task.domain.repository

import badang.android.taskit.feature_task.domain.model.Task

interface AlarmScheduler {
    fun onSchedule(task: Task)
    fun cancelSchedule(task: Task)
    fun cancelAllSchedules(tasks: List<Task>)
    fun onSnooze(task:Task)
}