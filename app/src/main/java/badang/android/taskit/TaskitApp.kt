package badang.android.taskit

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.data.local.data_store.DataPreferences
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class TaskitApp : Application() {


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        DataPreferences.init(this)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder"
            val descriptionText = "Taskit_Reminder"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constant.Notification.CHANNEL_ALARM_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}