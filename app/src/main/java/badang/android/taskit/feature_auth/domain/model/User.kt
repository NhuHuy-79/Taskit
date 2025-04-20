package badang.android.taskit.feature_auth.domain.model

import badang.android.taskit.feature_task.data.network.TaskDTO

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val tasks: List<TaskDTO> = emptyList()
)
