package badang.android.taskit.feature_task.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import badang.android.taskit.core.AppHelper
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import badang.android.taskit.feature_task.component.receiver.AlarmReceiver
import badang.android.taskit.feature_task.utils.AlarmUtils
import com.google.gson.Gson
import javax.inject.Inject


class AlarmSchedulerImp @Inject constructor (
    private val alarmManager: AlarmManager,
    private val context: Context,
) : AlarmScheduler {

    private val snoozeTime: Long = AlarmUtils.snoozeTime(5)

    override fun onSchedule(task: Task) {

        if (task.time == null) return

        val json = Gson().toJson(task)
        AppHelper.printLog("GSON", json)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constant.Extra.TASK_PARCEL, task)
            putExtra(Constant.Extra.TASK_GSON, json)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.time,
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        )
    }

    override fun cancelSchedule(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constant.Extra.TASK_PARCEL, task)
        }
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        )
    }

    override fun cancelAllSchedules(tasks: List<Task>) {
        tasks.forEach { task->
            cancelSchedule(task)
        }
    }

    override fun onSnooze(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constant.Extra.TASK_PARCEL, task)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            snoozeTime,
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        )
    }
}