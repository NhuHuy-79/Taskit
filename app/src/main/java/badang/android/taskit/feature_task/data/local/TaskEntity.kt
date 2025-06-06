package badang.android.taskit.feature_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val time: Long,
    val isDone: Boolean = false,
    val isEnabled: Boolean = true,
    val isSynced: Boolean = false
)
