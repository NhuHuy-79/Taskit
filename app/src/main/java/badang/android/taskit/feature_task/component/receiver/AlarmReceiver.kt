package badang.android.taskit.feature_task.component.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import badang.android.taskit.R
import badang.android.taskit.core.AppHelper
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.component.service.TaskService
import badang.android.taskit.feature_task.domain.model.Task
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent == null ) {
            AppHelper.printLog("INTENT", "null _ intent")
        }

        val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.Extra.TASK_PARCEL, Task::class.java)
        } else {
            Gson().fromJson(
                intent?.getStringExtra(Constant.Extra.TASK_GSON),
                Task::class.java

            )

        }



        /* Intent handler */
        val snoozeIntent = Intent(context, ActionReceiver::class.java).apply {
            putExtra(Constant.Extra.TASK_PARCEL, task)
            putExtra(Constant.Extra.TASK_GSON, task)
        }

        val snoozePendingIntent = PendingIntent.getBroadcast(context,
            task?.id?.hashCode() ?: 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)

        val completeIntent = Intent(context, TaskService::class.java).apply {
            putExtra(Constant.Extra.TASK_PARCEL, task)
            putExtra(Constant.Extra.TASK_GSON, task)
        }

        val completePendingIntent = PendingIntent.getService(context,
            task?.id.hashCode(), completeIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, Constant.Notification.CHANNEL_ALARM_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Taskit Reminder")
            .setContentText(task?.content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setAutoCancel(false)
            .addAction(R.drawable.icon_alarm, "SNOOZE", snoozePendingIntent)
            .addAction(R.drawable.ic_modal_check, "COMPLETE", completePendingIntent)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .setSilent(true)
            .build()

        val notificationId = (task?.id ?: System.currentTimeMillis()).hashCode()
        notificationManager.notify(notificationId, notification)
    }
}





