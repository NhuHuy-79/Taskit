package badang.android.taskit.feature_task.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("select * from task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("update task set isDone = not isDone where id =:id")
    suspend fun toggleCompletingTask(id: String)

    @Query("update task set isSynced = true where id = :id")

    suspend fun markTaskSynced(id:String)

    @Query("update task set isSynced = false where id =:id")
    suspend fun markTaskUnSynced(id: String)

    @Query("delete from task where id = :id")
    suspend fun deleteTask(id: String)

    @Query("update task set isAlarmed = false where id = :id")
    suspend fun setAlarm(id: String)

    @Query("delete from task")
    suspend fun deleteAllTasks()

    @Query("select * from task where id = :id" )
    suspend fun getTaskById(id: String): TaskEntity?
    
    @Query("select * from task where isSynced = false")
    suspend fun getTaskToSync(): List<TaskEntity>

}