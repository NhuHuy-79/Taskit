package badang.android.taskit.feature_task.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import badang.android.taskit.feature_task.data.repository.alarm.TASK_ID
import badang.android.taskit.feature_task.service.ActionService
import badang.android.taskit.feature_task.utils.Constant


class ActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val taskId: String = intent?.getStringExtra(Constant.Notification.EXTRA_TASK_ID) ?: ""
        val serviceIntent = Intent(context, ActionService::class.java).apply {
            putExtra(TASK_ID, taskId)
        }
        serviceIntent.action = intent?.action ?: "nothing"
        if (context != null) {
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}