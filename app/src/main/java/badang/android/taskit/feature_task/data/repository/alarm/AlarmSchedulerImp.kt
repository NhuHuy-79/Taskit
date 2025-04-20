package badang.android.taskit.feature_task.data.repository.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import badang.android.taskit.feature_task.receiver.AlarmReceiver
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.alarm.AlarmScheduler
import badang.android.taskit.feature_task.utils.AlarmUtils
import javax.inject.Inject

const val ALARM_TIME = "Alarm Time"
const val ALARM_CONTENT = "Alarm Content"
const val TASK_ID = "task id"
const val SNOOZE_TIME = "snooze_time"
class AlarmSchedulerImp @Inject constructor (
    private val alarmManager: AlarmManager,
    private val context: Context,
) : AlarmScheduler {

    private val snoozeTime: Long = AlarmUtils.snoozeTime(5)

    override fun onSchedule(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_TIME, task.time)
            putExtra(ALARM_CONTENT, task.content)
            putExtra(TASK_ID, task.id)
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
            putExtra(ALARM_TIME, task.time)
            putExtra(ALARM_CONTENT, task.content)
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

    override fun onSnooze(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TASK_ID, task.id)
            putExtra(SNOOZE_TIME, snoozeTime)
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