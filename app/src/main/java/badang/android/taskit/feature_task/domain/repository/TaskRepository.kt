package badang.android.taskit.feature_task.domain.repository

import badang.android.taskit.feature_task.data.local.room.TaskEntity
import badang.android.taskit.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun upsertTask(task: TaskEntity)
    suspend fun markTaskSynced(id: String)
    suspend fun markTaskUnSynced(id: String)
    suspend fun setAlarm(id: String)
    suspend fun toggleCompletingTask(id: String)
    suspend fun deleteTask(id: String)
    suspend fun deleteAllTasks()
    suspend fun getTaskToSync(): List<TaskEntity>
    suspend fun deleteAllTasks(taskList: List<Task>)
    suspend fun getTask(id: String): TaskEntity?
    suspend fun syncTask(list: List<TaskEntity>)
}