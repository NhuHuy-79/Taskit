package badang.android.taskit.feature_task.domain.repository.task.local

import badang.android.taskit.feature_task.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun upsertTask(task: TaskEntity)
    suspend fun hideTask(id: String)
    suspend fun toggleCompletingTask(id: String)
    suspend fun deleteTask(id: String)
    suspend fun showTask(id: String)
    suspend fun deleteAllTasks()
    suspend fun getTask(id: String): TaskEntity
}