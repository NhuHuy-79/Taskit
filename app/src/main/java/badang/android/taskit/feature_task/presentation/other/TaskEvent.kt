package badang.android.taskit.feature_task.presentation.other

import badang.android.taskit.feature_task.domain.model.Task


sealed class TaskEvent {
    data class Upsert(val id: String?, val content: String, val hour: Int, val minute: Int): TaskEvent()
    data class Enable(val id: String): TaskEvent()
    data class ToggleComplete(val id: String): TaskEvent()
    data class Delete(val task: Task): TaskEvent()
    data class OnTextChange(val value: String): TaskEvent()
    data class OnAlarmSet(val hour:Int, val minute: Int): TaskEvent()
    data object DeleteAll: TaskEvent()
}