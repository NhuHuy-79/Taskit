package badang.android.taskit.feature_task.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toDomain

import badang.android.taskit.feature_task.data.repository.alarm.TASK_ID
import badang.android.taskit.feature_task.domain.usecase.alarm.OnSnoozeUseCase
import badang.android.taskit.feature_task.domain.usecase.task.GetTaskUseCase
import badang.android.taskit.feature_task.domain.usecase.task.ToggleCompletingTaskUseCase
import badang.android.taskit.feature_task.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActionService : Service() {
    @Inject
    lateinit var toggleCompletingTaskUseCase: ToggleCompletingTaskUseCase
    @Inject
    lateinit var snoozeUseCase: OnSnoozeUseCase
    @Inject
    lateinit var getTaskUseCase: GetTaskUseCase

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val taskID = intent?.getStringExtra(TASK_ID) ?: return START_NOT_STICKY
        Toast.makeText(this, taskID, Toast.LENGTH_SHORT).show()
        scope.launch {
            when (intent.action){
                Constant.IntentAction.ACTION_SNOOZE -> {
                    val task = getTaskUseCase(taskID).toDomain()
                    snoozeUseCase(task)
                    Log.d("SERVICE_TASK", "$taskID")
                }

                Constant.IntentAction.ACTION_COMPLETE -> toggleCompletingTaskUseCase(taskID)
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}