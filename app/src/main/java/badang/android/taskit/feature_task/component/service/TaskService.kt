package badang.android.taskit.feature_task.component.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.use_case.task.ToggleCompletingTaskUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskService: Service() {

    @Inject
    lateinit var toggleCompletingTaskUseCase: ToggleCompletingTaskUseCase

    @Inject
    lateinit var notificationManager: NotificationManager


    private var job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @Suppress("DEPRECATION")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.Extra.TASK_PARCEL, Task::class.java)
        } else {
            intent?.getParcelableExtra(Constant.Extra.TASK_PARCEL)
        }

        if (task != null) {
            scope.launch {
                task.id?.let {
                    toggleCompletingTaskUseCase(task.id)
                }
            }

        }

        val notificationID = (task?.id ?: System.currentTimeMillis()).hashCode()
        notificationManager.cancel(notificationID)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}