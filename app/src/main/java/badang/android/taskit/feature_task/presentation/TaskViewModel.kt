package badang.android.taskit.feature_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toDomain
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toEntity
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.use_case.task.DeleteAllTasksUserCase
import badang.android.taskit.feature_task.domain.use_case.task.DeleteTaskUseCase
import badang.android.taskit.feature_task.domain.use_case.task.EnableTaskUseCase
import badang.android.taskit.feature_task.domain.use_case.task.GetAllTasksUseCase
import badang.android.taskit.feature_task.domain.use_case.task.ToggleCompletingTaskUseCase
import badang.android.taskit.feature_task.domain.use_case.task.UpsertTaskUseCase
import badang.android.taskit.feature_task.presentation.other.TaskEvent
import badang.android.taskit.feature_task.presentation.other.TaskState
import badang.android.taskit.feature_task.utils.AlarmUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val enableTaskUseCase: EnableTaskUseCase,
    getAllTasksUseCase: GetAllTasksUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleCompletingTaskUseCase: ToggleCompletingTaskUseCase,
    private val deleteAllTasksUserCase: DeleteAllTasksUserCase
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())

    val tasks = combine(_taskState, getAllTasksUseCase()){state, tasks ->
        state.copy(
            tasks = tasks.map { it.toDomain() }.sortedBy { !it.isDone }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TaskState()
    )

    fun onEvent(event: TaskEvent){
        when (event){
            is TaskEvent.Enable -> viewModelScope.launch {
                enableTaskUseCase(event.id)
            }
            is TaskEvent.ToggleComplete -> viewModelScope.launch {
                toggleCompletingTaskUseCase(event.id)
            }
            is TaskEvent.Upsert -> viewModelScope.launch {
                val task = Task(
                    id = event.id,
                    content = event.content,
                    isEnabled = !(event.hour == -1 || event.minute == -1),
                    time = AlarmUtils.convertToLong(event.hour, event.minute)
                )
                upsertTaskUseCase(task.toEntity())
                resetTextField()
            }

            is TaskEvent.OnTextChange -> {
                _taskState.update {
                    it.copy(
                        content = event.value
                    )
                }
            }

            is TaskEvent.OnAlarmSet -> {
                _taskState.update {
                    it.copy(
                        hour = event.hour,
                        minute = event.minute
                    )
                }
            }

            is TaskEvent.Delete -> {
                viewModelScope.launch {
                    deleteTaskUseCase(event.task)
                }
            }

            is TaskEvent.DeleteAll -> {
                viewModelScope.launch {
                    deleteAllTasksUserCase()
                }
            }

        }
    }

    private fun resetTextField(){
        _taskState.update {
            it.copy(
                content = ""
            )
        }
    }

    fun updateTaskState(task:Task){
        _taskState.update {
            it.copy(
                id = task.id,
                content = task.content
            )
        }
    }

    fun clearTimeState(){
        _taskState.update {
            it.copy(
                hour = -1,
                minute = -1,
            )
        }
    }

    fun resetState() {
        _taskState.update {
            it.copy(
                id = null,
                content = ""
            )
        }
    }

}
