package badang.android.taskit.feature_task.di

import android.app.AlarmManager
import android.content.Context
import badang.android.taskit.feature_task.domain.repository.alarm.AlarmScheduler
import badang.android.taskit.feature_task.data.repository.alarm.AlarmSchedulerImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlarmModule {

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