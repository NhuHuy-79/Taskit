package badang.android.taskit.feature_task.domain.model

data class Task(
    val id: String? = null,
    val content: String,
    val time: Long,
    val isDone: Boolean = false,
    val isAlarmed: Boolean = true,
)