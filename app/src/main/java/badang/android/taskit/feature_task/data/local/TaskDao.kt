package badang.android.taskit.feature_task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("select * from task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("update task set isEnabled = true where id = :id")
    suspend fun hideTask(id: String)

    @Query("update task set isDone = not isDone where id =:id")
    suspend fun toggleCompletingTask(id: String)

    @Query("delete from task where id = :id")
    suspend fun deleteTask(id: String)

    @Query("update task set isEnabled = false where id = :id")
    suspend fun showTask(id: String)

    @Query("delete from task")
    suspend fun deleteAllTasks()

    @Query("select * from task where id = :id" )
    suspend fun getTaskById(id: String): TaskEntity

}