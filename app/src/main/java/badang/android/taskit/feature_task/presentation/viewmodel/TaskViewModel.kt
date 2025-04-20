package badang.android.taskit.feature_task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toDomain
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toEntity
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.usecase.alarm.CancelScheduleUseCase
import badang.android.taskit.feature_task.domain.usecase.alarm.OnScheduleUseCase
import badang.android.taskit.feature_task.domain.usecase.alarm.OnSnoozeUseCase
import badang.android.taskit.feature_task.domain.usecase.task.DeleteAllTasksUserCase
import badang.android.taskit.feature_task.domain.usecase.task.DeleteTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.GetAllTasksUseCase
import badang.android.taskit.feature_task.domain.usecase.task.GetTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.HideTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.ShowTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.ToggleCompletingTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.UpsertTaskUseCase
import badang.android.taskit.feature_task.utils.AlarmUtils
import badang.android.taskit.feature_task.presentation.TaskEvent
import badang.android.taskit.feature_task.presentation.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STOP_TIMEOUT = 5000L

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val hideTaskUseCase: HideTaskUseCase,
    getAllTasksUseCase: GetAllTasksUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val showTaskUseCase: ShowTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleCompletingTaskUseCase: ToggleCompletingTaskUseCase,
    private val deleteAllTasksUserCase: DeleteAllTasksUserCase,
    private val onScheduleUseCase: OnScheduleUseCase,
    private val cancelScheduleUseCase: CancelScheduleUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val snoozeUseCase: OnSnoozeUseCase
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())

    val tasks = combine(_taskState, getAllTasksUseCase()){state, tasks ->
        state.copy(
            tasks = tasks.map { it.toDomain() }.sortedBy { !it.isDone }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(STOP_TIMEOUT),
        TaskState()
    )


    fun onEvent(event: TaskEvent){
        when (event){
            is TaskEvent.Hide -> viewModelScope.launch {
                hideTaskUseCase(event.id)
            }
            is TaskEvent.ToggleComplete -> viewModelScope.launch {
                toggleCompletingTaskUseCase(event.id)
            }
            is TaskEvent.Undo -> viewModelScope.launch {
                showTaskUseCase(event.id)
            }
            is TaskEvent.Upsert -> viewModelScope.launch {
                val task = Task(
                    event.id,
                    event.content,
                    AlarmUtils.convertToLong(event.hour, event.minute)
                )
                upsertTaskUseCase(task.toEntity())
                onScheduleUseCase(task)
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
                    deleteTaskUseCase(event.id)
                }
            }

            is TaskEvent.DeleteAll -> {
                viewModelScope.launch {
                    deleteAllTasksUserCase()
                }
            }

            is TaskEvent.CancelNotification -> {
                viewModelScope.launch {
                    val task = getTaskUseCase(event.id).toDomain()
                    onScheduleUseCase(task)
                }
            }
            is TaskEvent.SetNotification -> {
                viewModelScope.launch {
                    val task = getTaskUseCase(event.id).toDomain()
                    cancelScheduleUseCase(task)
                }
            }
        }
    }



    fun resetTextField(){
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

}
