package badang.android.taskit.feature_task.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import badang.android.taskit.R
import badang.android.taskit.feature_task.utils.Constant
import badang.android.taskit.feature_task.data.repository.alarm.ALARM_CONTENT
import badang.android.taskit.feature_task.data.repository.alarm.ALARM_TIME
import badang.android.taskit.feature_task.data.repository.alarm.TASK_ID
import badang.android.taskit.feature_task.service.ActionService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val time = intent?.getLongExtra(ALARM_TIME, -1L)
        val content = intent?.getStringExtra(ALARM_CONTENT)
        val taskId  = intent?.getStringExtra(TASK_ID)


        /* Intent handler */
        val snoozeIntent = Intent(context, ActionService::class.java).apply {
            action = Constant.IntentAction.ACTION_SNOOZE
            putExtra(Constant.Notification.EXTRA_TASK_ID, taskId)
        }

        val completeIntent = Intent(context, ActionService::class.java).apply {
            action = Constant.IntentAction.ACTION_COMPLETE
            putExtra(Constant.Notification.EXTRA_TASK_ID, taskId)
        }



        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Taskit Reminder")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setAutoCancel(true)
            .addAction(
                0, "Snooze", PendingIntent.getForegroundService(context,0,
                    snoozeIntent, PendingIntent.FLAG_IMMUTABLE )
            )
            .addAction(
                0, "Complete", PendingIntent.getForegroundService(context, 0,
                    completeIntent, PendingIntent.FLAG_IMMUTABLE)
            )
            .build()


        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

        val notificationId = (time ?: System.currentTimeMillis()).hashCode()
        notificationManager.notify(notificationId, builder)
    }



}





