package badang.android.taskit.feature_task.di

import android.content.Context
import androidx.room.Room
import badang.android.taskit.feature_task.data.local.MIGRATION_1_2
import badang.android.taskit.feature_task.data.local.TaskDao
import badang.android.taskit.feature_task.data.local.TaskDatabase
import badang.android.taskit.feature_task.data.repository.task.TaskRepositoryImp
import badang.android.taskit.feature_task.domain.repository.task.local.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImp(taskDao)
    }

    @Provides
    @Singleton
    fun provideDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task.db"
        ).addMigrations(MIGRATION_1_2)
            .build()

    }
}