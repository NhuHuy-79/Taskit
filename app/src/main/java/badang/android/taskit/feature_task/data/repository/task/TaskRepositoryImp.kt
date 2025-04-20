package badang.android.taskit.feature_task.data.repository.task

import badang.android.taskit.feature_task.data.local.TaskDao
import badang.android.taskit.feature_task.data.local.TaskEntity
import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    override suspend fun upsertTask(task: TaskEntity) {
        return taskDao.upsertTask(task)
    }

    override suspend fun hideTask(id: String) {
        return taskDao.hideTask(id)
    }

    override suspend fun showTask(id: String) {
        return taskDao.showTask(id)
    }

    override suspend fun toggleCompletingTask(id: String) {
        return taskDao.toggleCompletingTask(id)
    }

    override suspend fun deleteTask(id: String) {
        return taskDao.deleteTask(id)
    }

    override suspend fun deleteAllTasks() {
        return taskDao.deleteAllTasks()
    }

    override suspend fun getTask(id: String): TaskEntity {
        return taskDao.getTaskById(id)
    }
}