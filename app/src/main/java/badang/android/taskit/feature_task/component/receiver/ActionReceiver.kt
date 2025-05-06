package badang.android.taskit.feature_task.component.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global
import badang.android.taskit.core.AppHelper
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import badang.android.taskit.feature_task.domain.use_case.alarm.OnSnoozeUseCase
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class ActionReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var scheduler: AlarmScheduler

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var repository: TaskRepository

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        intent?.getStringExtra(Constant.Extra.TASK_GSON)?.let { AppHelper.printLog("Gson_tag", it) }

        val snoozeUseCase = OnSnoozeUseCase(scheduler)
        val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.Extra.TASK_PARCEL, Task::class.java)
        } else {
            Gson().fromJson(
                intent?.getStringExtra(Constant.Extra.TASK_GSON),
                Task::class.java
            )
        }

        if (task != null) {
            snoozeUseCase(task)
            val notificationID = (task.id ?: System.currentTimeMillis()).hashCode()
            notificationManager.cancel(notificationID)
        } else
            AppHelper.showToast(context, null, "Oops, something went wrong!")
    }

}


@OptIn(DelicateCoroutinesApi::class)
fun BroadcastReceiver.goAsync(
    context: CoroutineContext,
    block: suspend CoroutineScope.(BroadcastReceiver.PendingResult) -> Unit
){
    val pendingResult = goAsync()
    GlobalScope.launch {
        try {
            block(pendingResult)
        } finally {
            pendingResult.finish()
        }
    }
}