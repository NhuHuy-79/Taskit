package badang.android.taskit.feature_task.data.mapper

import badang.android.taskit.feature_task.data.local.room.TaskEntity
import badang.android.taskit.feature_task.data.network.TaskDTO
import badang.android.taskit.feature_task.domain.model.Task
import java.util.UUID

class TaskMapper {

    companion object {
        fun TaskEntity.toDomain() = Task(
            id = this.id,
            content = this.content,
            time = this.time,
            isDone = this.isDone,
            isEnabled = this.isAlarmed,
        )

        fun Task.toEntity() = TaskEntity(
            id = id ?: UUID.randomUUID().toString(),
            content = this.content,
            time = this.time,
            isDone = this.isDone,
            isAlarmed = this.isEnabled,
            isSynced = false
        )


        fun TaskEntity.toNetWork(): TaskDTO = TaskDTO(
            id = this.id ?: "",
            content = this.content,
            isDone =  this.isDone,
            timeStamp = this.time
        )

        fun TaskEntity.toHashMap() = mapOf(
            "content" to this.content,
            "isDone" to this.isDone,
            "timeStamp" to this.time
        )
    }
}