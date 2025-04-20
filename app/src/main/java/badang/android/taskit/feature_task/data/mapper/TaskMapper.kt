package badang.android.taskit.feature_task.data.mapper

import badang.android.taskit.feature_task.data.local.TaskEntity
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
            isAlarmed = this.isEnabled,
        )

        fun Task.toEntity() = TaskEntity(
            id = id ?: UUID.randomUUID().toString(),
            content = this.content,
            time = this.time,
            isDone = this.isDone,
            isEnabled = this.isAlarmed,
            isSynced = false
        )

        fun Task.toNetWork(): TaskDTO = TaskDTO(
            id = this.id ?: "",
            content = this.content,
            isDone =  this.isDone,
            time = this.time,
            isEnabled = this.isAlarmed
        )
    }
}