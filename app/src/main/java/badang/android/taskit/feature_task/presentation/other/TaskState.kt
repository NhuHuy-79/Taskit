package badang.android.taskit.feature_task.presentation.other

import badang.android.taskit.feature_task.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val id: String? = null,
    val content: String = "",
    val hour: Int = -1,
    val minute: Int = -1,
)