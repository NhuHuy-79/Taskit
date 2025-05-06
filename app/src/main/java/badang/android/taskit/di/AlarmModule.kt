package badang.android.taskit.di

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import badang.android.taskit.R
import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.data.repository.AlarmSchedulerImp
import badang.android.taskit.feature_task.domain.repository.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {


    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        return notificationManager
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(
        alarmManager: AlarmManager,
        @ApplicationContext context: Context
    ): AlarmScheduler {
        return AlarmSchedulerImp(alarmManager, context )
    }


    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager{
        return context.getSystemService(AlarmManager::class.java)
    }
}