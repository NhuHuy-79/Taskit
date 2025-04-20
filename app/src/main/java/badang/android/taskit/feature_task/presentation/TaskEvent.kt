package badang.android.taskit.feature_task.presentation


sealed class TaskEvent {
    data class Upsert(val id: String?, val content: String, val hour: Int, val minute: Int): TaskEvent()
    data class Hide(val id: String): TaskEvent()
    data class ToggleComplete(val id: String): TaskEvent()
    data class Undo(val id: String): TaskEvent()
    data class Delete(val id:String): TaskEvent()
    data class OnTextChange(val value: String): TaskEvent()
    data class OnAlarmSet(val hour:Int, val minute: Int): TaskEvent()
    data object DeleteAll: TaskEvent()
    data class SetNotification(val id: String): TaskEvent()
    data class CancelNotification(val id: String): TaskEvent()
}