package badang.android.taskit.feature_task.data.network

import java.util.UUID

data class TaskDTO (
    val id: String = UUID.randomUUID().toString(),
    val content: String = "",
    val time: Long? = null,
    val isDone: Boolean = false,
    val isEnabled: Boolean = true
)
